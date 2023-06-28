package com.pilot.cwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(CwtApplication.class, args);
    }

}
