package com.pilot.cwt.service;

import java.sql.Timestamp;
import java.util.Date;
import com.pilot.cwt.JWTUtility.JWTUtility;
import com.pilot.cwt.entity.Token;
import com.pilot.cwt.repository.JPATokenRepository;
import org.apache.kafka.streams.processor.To;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    @Autowired
    private JPATokenRepository jpaTokenRepository;

    Token tokenTable;
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private KafkaTemplate<String, String > kafkaTemplate;

    Logger logger = LoggerFactory.getLogger(TokenService.class);


    @KafkaListener(topics = "topicEmailBack", groupId = "TRAVEL - CWT")
    public Token reganerateToken(String email) {
        logger.info("receive from concur said the token expired, the email is: " + email);
        jpaTokenRepository.deleteById(tokenTable.getEmail());
        tokenTable = createNewToken(email);
        logger.info("reganerate a token for concur : " + tokenTable);
        return tokenTable;
    }


//    @KafkaListener(topics = "topicUserCustomerInfo", groupId = "TRAVEL - CWT")

    @KafkaListener(topics = "topicUserEmail", groupId = "TRAVEL - CWT")
    public Token getTokenByEmail(String email) {
        logger.info("receive Email from CustomerSystem");
        if (jpaTokenRepository.findByEmail(email) == null) {
            logger.info("there is no this email's token in db, we generate on now");
            tokenTable =  createNewToken(email);
            logger.info("successful generate token which is: " +  tokenTable.toString());
        } else {
            tokenTable = jpaTokenRepository.findByEmail(email);
            //Check if date is expired
            Timestamp ts=new Timestamp(System.currentTimeMillis());
            Date CurDate=ts;
            Date future2minDate=ts;
            future2minDate.setTime(future2minDate.getTime()+ 120*1000);//Expire in 2 minutes
            Date ExpireDate = jpaTokenRepository.findByEmail(email).getExpiration_timestamp();
            //For expired token, create a new one
            if(ExpireDate.before(future2minDate) || ExpireDate.before(CurDate)){
                jpaTokenRepository.deleteById(tokenTable.getEmail());
                tokenTable = createNewToken(email);
                logger.info("Since the token expired or will expired in 2 min. Create a new token: " + tokenTable.toString());
            }else{
                logger.info("Use the existing token: " + tokenTable.toString());
            }
        }
        logger.info("The token is : " + tokenTable.getToken());
        return tokenTable;
    }

    private Token createNewToken(String email){
        String token = jwtUtility.generateToken(email);
        Date timestamp = jwtUtility.getExpirationDateFromToken(token);
        Token tokenTable = new Token(email,token,timestamp);
        jpaTokenRepository.save(tokenTable);
        return tokenTable;
    }
}
