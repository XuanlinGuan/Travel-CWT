package com.pilot.concur.service;

import java.util.Optional;
import com.pilot.concur.configuration.SpringUnitTestBase;
import com.pilot.concur.entity.Customer;
import com.pilot.concur.repository.JPACustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConcurServiceTest extends SpringUnitTestBase {

    @Qualifier("createConcurService")
    @Autowired
    ConcurService concurService;

    @Qualifier("concur")
    @Autowired
    KafkaTemplate kafkaTemplate;

    @MockBean
    JPACustomerRepository customerRepository;

    @Before
    public void setUp() {
        Customer customer = new Customer();
        customer.setLastName("Guan");
        customer.setEmail("guanbuliao@gmail.com");
        customer.setFirstName("Xuanlin");
        customer.setId("01");
        Mockito.when(customerRepository.findByEmail("guanbuliao@gmail.com")).thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.findById("01")).thenReturn(Optional.of(customer));
    }

    @Test
    public void findByEmail() {
        Optional<Customer> cus = concurService.findByEmail("guanbuliao@gmail.com");
        assertThat(cus.get().getEmail()).isEqualTo("guanbuliao@gmail.com");
    }

    @Test
    public void saveCustomerTest() {
        Customer c = new Customer();
        c.setId("02");
        c.setFirstName("xx");
        c.setEmail("cc@gamil.com");
        c.setLastName("cc");
        String s = concurService.saveCustomer(c);
        assertThat(s.equals("successful create"));
    }

    @Test
    public void deleteCustomerTest() {
        String s = concurService.deleteCustomer("01");
        assertThat(s.equals("successful delete"));
    }

    @Test
    public void getCustomerByIdTest() {
        Optional<Customer> c = concurService.getCustomerById("01");
        assertThat(c.get().getEmail()).isEqualTo("guanbuliao@gmail.com");
    }

    @Test
    public void updateCustomerTest() {
        Customer customer = new Customer();
        customer.setLastName("Guan");
        customer.setEmail("guanbuliao@gmail.com");
        customer.setFirstName("Xuanlin");
        customer.setId("01");
        concurService.updateCustomer(customer);
    }

    @Test
    public void findByTokenTest() {
        Optional<Customer> c = concurService.findByToken("eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6Ijk5OTk5QGdtYWlsIiwiZmlyc3ROYW1lIjoiOTkiLCJsYXN0TmFtZSI6Ijk5Iiwic3ViIjoiOTk5OTlAZ21haWwiLCJpYXQiOjE2ODc4MDQ3NDUsImV4cCI6MTY4NzgwNDkyNX0.hkU2Qt2zqKhL6uDmjrhcxnzg0-tk69tNdT754DATa2HEGTyr8ylIJhxI5EijNKeSXwvHD_F2DyV4b94eRZsATA\n");
    }

}
