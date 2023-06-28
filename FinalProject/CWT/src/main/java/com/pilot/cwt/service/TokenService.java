package com.pilot.cwt.service;

import java.sql.Timestamp;
import java.util.Date;

import com.pilot.cwt.JWTUtility.JWTUtility;
import com.pilot.cwt.entity.Customer;
import com.pilot.cwt.entity.Login;
import com.pilot.cwt.entity.Token;
import com.pilot.cwt.repository.JPATokenRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TokenService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JPATokenRepository jpaTokenRepository;

    Token tokenTable;

    @Autowired
    private JWTUtility jwtUtility;

    Logger logger = LoggerFactory.getLogger(TokenService.class);

    /**
    * @Description: if the token send to concur is expire, concur will send the customer info back
    * @Param: [customer]
    * @return: com.pilot.cwt.entity.Token
    * @Author: Xuanlin Guan
    */
    @KafkaListener(topics = "topicEmailBack", groupId = "TRAVEL-CWT")
    public Token reganerateToken(String customer) {
        logger.info("receive from concur said the token expired, the email is: " + customer);

        Customer user = new Customer();

        try {
            user = objectMapper.readValue(customer, Customer.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.info("receive user from concur service send back : --" + user);

        jpaTokenRepository.deleteById(user.getEmail());
        Token token = createNewToken(user.getEmail(), user.getFirstName(), user.getLastName());
        logger.info("regenerate a token for concur : " + token.getToken());
        return token;
    }

    /**
    * @Description: receive the customer info from Customer System
    * @Param: [login]
    * @return: com.pilot.cwt.entity.Token
    * @Author: Xuanlin Guan
    */
    @KafkaListener(topics = "topicUserCustomerInfo", groupId = "${spring.kafka.consumer.group-id}")
    public Token getTokenByEmail(String login) {

        logger.info("The login info is : " + login);

        Login user = new Login();

        try {
            user = objectMapper.readValue(login, Login.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.info("receive Email from CustomerSystem : ~~" + user);
        if (jpaTokenRepository.findByEmail(user.getEmail()) == null) {
            logger.info("there is no this email's token in db, we generate on now");
            tokenTable =  createNewToken(user.getEmail(), user.getFirstName(), user.getLastName());
            logger.info("successful generate token which is: " +  tokenTable.toString());
        } else {
            tokenTable = jpaTokenRepository.findByEmail(user.getEmail());
            //Check if date is expired
            Timestamp ts=new Timestamp(System.currentTimeMillis());
            Date CurDate=ts;
            Date future2minDate=ts;
            future2minDate.setTime(future2minDate.getTime()+ 120*1000);//Expire in 2 minutes
            Date ExpireDate = jpaTokenRepository.findByEmail(user.getEmail()).getExpiration_timestamp();
            //For expired token, create a new one
            if(ExpireDate.before(future2minDate) || ExpireDate.before(CurDate)){
                jpaTokenRepository.deleteById(tokenTable.getEmail());
                tokenTable = createNewToken(user.getEmail(), user.getFirstName(), user.getLastName());
                logger.info("Since the token expired or will expired in 2 min. Create a new token: " + tokenTable.toString());
            }else{
                logger.info("Use the existing token: " + tokenTable.toString());
            }
        }
        logger.info("The token is : " + tokenTable.getToken());
        return tokenTable;
    }

    /**
    * @Description: create a new token
    * @Param: [email, firstName, lastName]
    * @return: com.pilot.cwt.entity.Token
    * @Author: Xuanlin Guan
    */
    private Token createNewToken(String email, String firstName, String lastName){
        logger.info("mail:" + email + " fistName: " + firstName + " lastName: " + lastName);
        String  token = jwtUtility.generateToken(email,firstName,lastName);
        Date timestamp = jwtUtility.getExpirationDateFromToken(token);
        Token tokenTable = new Token(email,token,timestamp);
        jpaTokenRepository.save(tokenTable);
        return tokenTable;
    }
}
