package com.pilot.customersystem.configuration;

import java.util.HashMap;
import java.util.Map;
import com.pilot.customersystem.controller.LogInController;
import com.pilot.customersystem.entity.Login;
import com.pilot.customersystem.service.LoginService;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class TestConfig {

    @Bean(value = "createLoginController")
    public LogInController createLoginController() {
        return new LogInController();
    }

    @Bean(value = "createLoginService")
    public LoginService createLoginService() {
        return new LoginService();
    }







    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, Login> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Login> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
