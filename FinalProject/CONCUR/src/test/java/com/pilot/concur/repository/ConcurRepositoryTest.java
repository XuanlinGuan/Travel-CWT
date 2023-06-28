package com.pilot.concur.repository;

import java.util.Optional;
import com.pilot.concur.entity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ConcurRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JPACustomerRepository customerRepository;

    @Before
    public void setUp() {
        entityManager.clear();
    }

    @Test
    public void whenFindById_thenReturnCustomer() {
        //given
        Customer customer = new Customer();
        customer.setLastName("Guan");
        customer.setEmail("guanbuliao@gmail.com");
        customer.setFirstName("Xuanlin");
        customer.setId("01");

        Customer Guan = new Customer();
        customerRepository.save(customer);

        //when
        Optional<Customer> result = customerRepository.findById("01");
        if (result.isPresent()) {
            Guan = (Customer) result.get();
        }

        //then
        assertThat(Guan.getLastName()).isEqualTo("Guan");
    }

}
