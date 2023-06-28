package com.pilot.concur.configuration;

import java.util.HashMap;
import java.util.Map;
import com.pilot.concur.controller.CustomerController;
import com.pilot.concur.entity.Customer;
import com.pilot.concur.service.ConcurService;
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

    @Bean(value = "createCustomerController")
    public CustomerController createCustomerController() {
        return new CustomerController();
    }

    @Bean(value = "createConcurService")
    public ConcurService createConcurService() {
        return new ConcurService();
    }



    /**
     * @Description: Here is to set the kafka boot Strap address
     * @Param:
     * @return:
     * @Author: Xuanlin Guan
     */
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    /**
     * @Description: ProducerFactory serializer the object so we can use kafka to transport
     * @Param: []
     * @return: org.springframework.kafka.core.ProducerFactory<java.lang.String,com.pilot.concur.entity.Customer>
     * @Author: Xuanlin Guan
     */
    public ProducerFactory<String, Customer> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * @Description: Add a bean to container
     * @Param: []
     * @return: a bean KafkaTemplate
     * @Author: Xuanlin Guan
     */
    @Bean(value = "concur")
    public KafkaTemplate<String, Customer> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
