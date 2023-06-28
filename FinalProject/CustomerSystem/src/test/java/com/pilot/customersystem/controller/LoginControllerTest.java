package com.pilot.customersystem.controller;

import com.pilot.customersystem.configuration.SpringUnitTestBase;
import com.pilot.customersystem.entity.Login;
import com.pilot.customersystem.service.LoginService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
public class LoginControllerTest extends SpringUnitTestBase {

    @Qualifier("createLoginController")
    @Autowired
    LogInController logInController;

    @SpyBean
    LoginService loginService;

    @Before
    public void setUp() {
        Login login = new Login();
        login.setEmail("guanbuliao@gmail.com");
        login.setLastName("guan");
        login.setFirstName("xuanlin");
        Mockito.when(loginService.sendLoginInfo(login)).thenReturn("Successful send the customer Information");
    }



    @Test
    public void testLogIn() {
        Login login = new Login();
        login.setEmail("guanbuliao@gmail.com");
        login.setLastName("guan");
        login.setFirstName("xuanlin");
        String s = logInController.login(login);
        assertThat(s).isEqualTo("Successful send the customer Information");


    }

    @Test
    public void testStart() {
        String s = logInController.start();
        assertThat(s).isEqualTo("Welcome to TRAVEl-CWT System");

    }
}
