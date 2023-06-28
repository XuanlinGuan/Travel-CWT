package com.pilot.cwt;

import com.pilot.cwt.repository.CustomerRepositoryTest;
import com.pilot.cwt.service.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;




@SpringBootTest
@RunWith(Suite.class) //Suite.class 表示 获取一个类的'Class'对象，当使用suite.class时，
// 通常是在测试框架中定义测试套件时使用。测试套件是一组测试用例的集合，可以一起执行多个测试。
//测试框架通常提供一个特定的类或方法用于创建测试套件，而suite.class用于获取表示该测试套件的Class对象。
// 说直白点就是 把这几个class 作为一个 测试套件一起测试
@Suite.SuiteClasses({CustomerRepositoryTest.class
})
class CwtApplicationTests {

//    @Test
//    void contextLoads() {
//    }

}
