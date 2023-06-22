package com.pilot.customersystem.service;


import com.pilot.customersystem.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.pattern.PathPattern;

@Service
public class LoginService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    Logger logger = LoggerFactory.getLogger(LoginService.class);

    public String sendLoginInfo(Customer customer) {
        logger.info("receive the sendLoginInfo request");
        kafkaTemplate.send("topicUserEmail", customer.getEmail());
//        kafkaTemplate.send("topicUserLastName", customer.getLastName());
//        kafkaTemplate.send("topicUserFirstName", customer.getFirstName());
        kafkaTemplate.send("topicUserCustomerInfo", customer.toString());
        logger.info("send the sendLoginInfo request to CWT");
        return "Successful send the customer Information";
    }

    @KafkaListener(topics = "topicCustomer", groupId = "TRAVEL - CWT")
    public void getCustomerInfo( String message) {
        logger.info("Receive message from CONCUR Successfully!" + message);
    }


}
