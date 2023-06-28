package com.pilot.concur.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.pilot.concur.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JPACustomerRepository extends JpaRepository<Customer, String> {
    /**
    * @Description: use email to find the customer form SQL
    * @Param: [email]
    * @return: java.util.Optional<com.pilot.concur.entity.Customer>
    * @Author: Xuanlin Guan
    */
    @Query(value = "SELECT c.* FROM Customer c WHERE c.email =:email", nativeQuery = true)
    Optional<Customer> findByEmail(@Param("email") String email);

}
