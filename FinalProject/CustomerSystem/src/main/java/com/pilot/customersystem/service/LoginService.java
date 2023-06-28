package com.pilot.customersystem.service;


import com.pilot.customersystem.entity.Customer;
import com.pilot.customersystem.entity.Login;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private KafkaTemplate<String, Login> kafkaTemplate;
    Logger logger = LoggerFactory.getLogger(LoginService.class);


    /**
    * @Description: send the login info to CWT
    * @Param: [login]
    * @return: java.lang.String
    * @Author: Xuanlin
    */
    public String sendLoginInfo(Login login) {
        logger.info("receive the sendLoginInfo request");
        kafkaTemplate.send("topicUserCustomerInfo", login);
        logger.warn("send the sendLoginInfo request to CWT" + login );
        return "Successful send the customer Information";
    }

    /**
    * @Description: receive the customer info from CWT if the login is passed
    * @Param: [message]
    * @return: void
    * @Author: Xuanlin Guan
    */
    @KafkaListener(topics = "topicCustomer", groupId = "TRAVEL - CWT")
    public void getCustomerInfo( String message) {
        logger.info("Receive message from CONCUR Successfully!!!!!!!!" + message);
    }


}
