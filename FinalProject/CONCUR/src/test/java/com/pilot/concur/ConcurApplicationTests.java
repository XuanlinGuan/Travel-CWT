package com.pilot.concur;

import com.pilot.concur.controller.CustomerControllerTest;
import com.pilot.concur.repository.ConcurRepositoryTest;
import com.pilot.concur.service.ConcurService;
import com.pilot.concur.service.ConcurServiceTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        CustomerControllerTest.class,
        ConcurServiceTest.class,
        ConcurRepositoryTest.class

})
class ConcurApplicationTests {

//    @Test
//    void contextLoads() {
//    }

}
