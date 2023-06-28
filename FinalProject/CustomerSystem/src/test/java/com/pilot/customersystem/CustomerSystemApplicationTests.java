package com.pilot.customersystem;


import com.pilot.customersystem.controller.LoginControllerTest;
import com.pilot.customersystem.service.LoginService;
import com.pilot.customersystem.service.LoginServiceTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@SpringBootTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginControllerTest.class,
        LoginServiceTest.class
})
class CustomerSystemApplicationTests {

//    @Test
//    void contextLoads() {
//    }

}
