package com.pilot.cwt.repository;

import javax.swing.Spring;
import javax.xml.crypto.Data;
import com.pilot.cwt.entity.Login;
import com.pilot.cwt.entity.Token;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JPATokenRepository tokenRepository;

    @Before
    public void setUp() {
        entityManager.clear();
    }

    @Test
    public void whenFindByToken_thenReturnCustomer() {

        Token token = new Token();
        token.setToken("eyJhbGciOiJIUzUxMiJ9.eyJlbWFpbCI6IjIyMjIyQGdtYWlsIiwiZmlyc3ROYW1lIjoiYmIiLCJsYXN0TmFtZSI6ImFhIiwic3ViIjoiMjIyMjJAZ21haWwiLCJpYXQiOjE2ODc0OTM4NjEsImV4cCI6MTY4NzQ5NDA0MX0.HHyMb5gIkl0EUldFlsoHT9kap-aF3PARZqiW54HqkCEhdD5J6vRHdwO1BFcWD3b_evEF11yDw6hf5G7Qq1w2bQ");
        token.setEmail("22222@gmail");

        Date d = new Date();
        d.getTime();

        token.setExpiration_timestamp(d);

        tokenRepository.save(token);

        //when
        Token result = tokenRepository.findByEmail("22222@gmail");

        Token t = new Token();
        t = (Token) result;

        //then
        assertThat(t.getEmail()).isEqualTo("22222@gmail");
    }

}
