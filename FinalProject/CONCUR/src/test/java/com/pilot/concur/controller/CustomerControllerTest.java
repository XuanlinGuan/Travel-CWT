package com.pilot.concur.controller;

import static org.mockito.ArgumentMatchers.anyString;
import java.util.Optional;
import com.pilot.concur.configuration.SpringUnitTestBase;
import com.pilot.concur.entity.Customer;
import com.pilot.concur.service.ConcurService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class CustomerControllerTest extends SpringUnitTestBase {

    @Qualifier("createCustomerController")
    @Autowired
    CustomerController customerController;

    @MockBean
    ConcurService concurServiceInTest;

    @Before
    public void setUp() {
        Customer customer = new Customer();
        customer.setLastName("aa");
        customer.setEmail("aa@gmail.com");
        customer.setId("00");
        Mockito.when(concurServiceInTest.getCustomerById(anyString())).thenReturn(Optional.of(customer));
        Mockito.when(concurServiceInTest.findByEmail(anyString())).thenReturn(Optional.of(customer));
        Mockito.when(concurServiceInTest.findByToken(anyString())).thenReturn(Optional.of(customer));
        Mockito.when(concurServiceInTest.saveCustomer(customer)).thenReturn("This email has been used!");
        Mockito.when(concurServiceInTest.deleteCustomer(anyString())).thenReturn("delete");
    }

    @Test
    public void testCustomerController() {
        String id = "00";
        Optional<Customer> customerById = customerController.getCustomerById(id);
        assertThat(customerById.get().getLastName()).isEqualTo("aa");


        String email = "aa@gmail.com";
        Optional<Customer> customerByEmail = customerController.getCustomerByEmail(email);
        assertThat(customerByEmail.get().getEmail()).isEqualTo("aa@gmail.com");

        String token = "aaaaaaaaaaa";
        Customer customerByToken = customerController.findCustomerInfoByToken(token);
        assertThat(customerByToken.getEmail()).isEqualTo("aa@gmail.com");

        Customer cs = new Customer();
        cs.setId("11");
        cs.setEmail("neverused@gamil.com");
        cs.setLastName("bb");
        cs.setFirstName("vv");
        cs.setAddress("dfsdf");
        String s = customerController.createCustomer(cs);
        assertThat(s).isEqualTo("This email has been used!");

        String del = customerController.deleteCustomer(id);
        assertThat(del).isEqualTo("delete");

    }

}
