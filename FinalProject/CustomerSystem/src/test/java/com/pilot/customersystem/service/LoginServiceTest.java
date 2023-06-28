package com.pilot.customersystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.pilot.customersystem.configuration.SpringUnitTestBase;
import com.pilot.customersystem.entity.Login;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
public class LoginServiceTest extends SpringUnitTestBase {

    @Qualifier("createLoginService")
    @Autowired
    LoginService loginService;

    @MockBean
    KafkaTemplate kafkaTemplate;



    @Test
    public void testSendLoginInfo() {
        Login login = new Login();
        login.setEmail("guanbuliao@gmail.com");
        login.setLastName("guan");
        login.setFirstName("xuanlin");
        String s =  loginService.sendLoginInfo(login);
        assertThat(s).isEqualTo("Successful send the customer Information");
    }


}
