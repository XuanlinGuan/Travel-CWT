package com.pilot.concur.service;

import java.util.Date;
import java.util.Optional;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pilot.concur.entity.Customer;
import com.pilot.concur.repository.JPACustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConcurService {

    @Autowired
    @Qualifier("concur")
    private KafkaTemplate<String,Customer> kafkaTemplate;
    private Logger logger = LoggerFactory.getLogger("Concur Service");

    @Autowired
    private JPACustomerRepository jpaCustomerRepository;

    /**
    * @Description: try to find customer form db by email
    * @Param: [email]
    * @return: java.util.Optional<com.pilot.concur.entity.Customer>
    * @Author: Xuanlin Guan
    */
    public Optional<Customer> findByEmail(String email) {
        logger.info("receive the request to find customer by email " + email);
        return jpaCustomerRepository.findByEmail(email);
    }

    /**
    * @Description: get the customer form controller and then save the customer to db
    * @Param: [customer]
    * @return: java.lang.String
    * @Author: Xuanlin Guan
    */
    public String saveCustomer(Customer customer) {
        logger.info("receive the request to create customer " + customer.getLastName());
        Optional<Customer> findCustomer = jpaCustomerRepository.findById(customer.getId());
        if (findCustomer.isPresent()) return "The Customer is already exist, please use another Id";
        jpaCustomerRepository.save(customer);
        logger.info("Send the create " + customer.getLastName() + " request to SQL");
        return "successful create";
    }

    /**
    * @Description: use the id to find the customer from db and then remove the customer from db
    * @Param: [id]
    * @return: java.lang.String
    * @Author: Xuanlin Guan
    */
    public String deleteCustomer(String id) {
        logger.info("receive the request to delete customer id " + id);
        Optional<Customer> findCustomer = jpaCustomerRepository.findById(id);
        if (!findCustomer.isPresent()) return "There is no this id customer";
        jpaCustomerRepository.deleteById(id);
        logger.info("Send the delete id: " + id + " request to SQL");
        return "successful delete";
    }

    /**
    * @Description: get the customer info from db by the customer id
    * @Param: [id]
    * @return: java.util.Optional<com.pilot.concur.entity.Customer>
    * @Author: Xuanlin Guan
    */
    public Optional<Customer> getCustomerById(String id) {
        logger.info("receive the request to find the customer by id " + id);
        Optional<Customer> findCustomer = jpaCustomerRepository.findById(id);
        logger.info("get the result of customer of id is " + id);
        return findCustomer;
    }

    /**
    * @Description: update the customer info
    * @Param: [customer]
    * @return: void
    * @Author: Xuanlin Guan
    */
    public void updateCustomer( Customer customer) {
        logger.info("receive the request to updateCustomer" + customer.getId());
        jpaCustomerRepository.save(customer);
        logger.info("Send the update to DB");
    }

    /**
    * @Description: use the token which from CWT to get the customer info
    * @Param: [token]
    * @return: java.util.Optional<com.pilot.concur.entity.Customer>
    * @Author: Xuanlin Guan
    */
    public Optional<Customer> findByToken(String token) {
        logger.info("Receive token from CWT, now decode the token");
        DecodedJWT decodedJWT = JWT.decode(token);
        String email = decodedJWT.getClaim("sub").asString();

        String firstName = decodedJWT.getClaim("firstName").asString();
        String lastName = decodedJWT.getClaim("lastName").asString();

        logger.info("This email get after decode the token is: " + email + " " + firstName + " " + lastName);

        Date expireTime = decodedJWT.getClaim("exp").asDate();
        Date curTime = new Date(System.currentTimeMillis());
        //check time expired or not
        if(expireTime.before(curTime)){
            logger.warn("The token expire ");
            Customer customer = new Customer();
            customer.setEmail(email);
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            sendTokenInfo(customer);
            logger.info("Token has been expired and send back to cwt : " + customer.toString());
            return null;
        }
        Optional<Customer> customer = jpaCustomerRepository.findByEmail(email);
        if (customer.isPresent()) {
            logger.info(customer.get().toString());

            logger.warn( " LastName:" + customer.get().getLastName());
            logger.warn( "FirstName:" + customer.get().getFirstName());

            if ( customer.get().getFirstName().equals(firstName) && customer.get().getLastName().equals(lastName)){
                logger.info("return the customer : " + customer.get());
                sendCustomerInfo(customer.get());
                return customer;
            }
            logger.warn("The last name or first name is not right.");
        } else {
            logger.warn("The customer not find");
        }
        return null;
    }

    /**
    * @Description: since the token from cwt is expired so need send bake the customer info back to cwt and then regenerate the token
    * @Param: [customer]
    * @return: void
    * @Author: Xuanlin Guan
    */
    private void sendTokenInfo(Customer customer){
        logger.info("send the token back to CWT since expire" + customer.toString());
        kafkaTemplate.send("topicEmailBack", customer);
        logger.info("Send email to CWT Successfully!");
    }

    /**
    * @Description: send the customer info to CustomerSystem
    * @Param: [customer]
    * @return: void
    * @Author: Xuanlin Guan
    */
    private void sendCustomerInfo(Customer customer){
        logger.info("send customer back to CustomerSystem" + customer);
        kafkaTemplate.send("topicCustomer",customer);
        logger.info("Send customer information to CustomerSystem Successfully!");
    }

}
