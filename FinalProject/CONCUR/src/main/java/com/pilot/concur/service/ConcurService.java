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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ConcurService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    private Logger logger = LoggerFactory.getLogger("Concur Service");

    @Autowired
    private JPACustomerRepository jpaCustomerRepository;

    public Optional<Customer> findByEmail(String email) {
        logger.info("receive the request to find customer by email " + email);
        return jpaCustomerRepository.findByEmail(email);
    }


    public String saveCustomer(Customer customer) {
        logger.info("receive the request to create customer " + customer.getLastName());
        Optional<Customer> findCustomer = jpaCustomerRepository.findById(customer.getId());
        if (findCustomer.isPresent()) return "The Customer is already exist, please use another Id";
        jpaCustomerRepository.save(customer);
        logger.info("Send the create " + customer.getLastName() + " request to SQL");
        return "successful create";
    }

    public String deleteCustomer(String id) {
        logger.info("receive the request to delete customer id " + id);
        Optional<Customer> findCustomer = jpaCustomerRepository.findById(id);
        if (!findCustomer.isPresent()) return "There is no this id customer";
        jpaCustomerRepository.deleteById(id);
        logger.info("Send the delete id: " + id + " request to SQL");
        return "successful delete";
    }

    public Optional<Customer> getCustomerById(String id) {
        logger.info("receive the request to find the customer by id " + id);
        Optional<Customer> findCustomer = jpaCustomerRepository.findById(id);
        logger.info("get the result of customer of id is " + id);
        return findCustomer;
    }

    public void updateCustomer( Customer customer) {
        logger.info("receive the request to updateCustomer" + customer.getId());
        jpaCustomerRepository.save(customer);
        logger.info("Send the update to DB");
    }

    public Optional<Customer> findByToken(String token) {
        logger.info("Receive token from CWT, now decode the token");
        DecodedJWT decodedJWT = JWT.decode(token);
        String email = decodedJWT.getClaim("sub").asString();
        logger.info("This email get after decode the token is: " + email);
        Date expireTime = decodedJWT.getClaim("exp").asDate();
        Date curTime = new Date(System.currentTimeMillis());
        //check time expired or not
        if(expireTime.before(curTime)){
            sendTokenInfo(email);
            logger.info("Token has been expired!");
            return null;
        }
        Optional<Customer> customer = jpaCustomerRepository.findByEmail(email);
        if (customer.isPresent()) {
            logger.info("return the customer");
            sendCustomerInfo(customer.get().toString());
            return customer;
        } else {
            logger.info("The customer not retrive");
            return null;
        }
    }

    private void sendTokenInfo(String email){
        kafkaTemplate.send("topicEmailBack",email);
        logger.info("Send email to CWT Successfully!");
    }

    private void sendCustomerInfo(String customer){
        logger.info("send customer back to CustomerSystem" + customer);
        kafkaTemplate.send("topicCustomer",customer);
        logger.info("Send customer information to CustomerSystem Successfully!");
    }

}
