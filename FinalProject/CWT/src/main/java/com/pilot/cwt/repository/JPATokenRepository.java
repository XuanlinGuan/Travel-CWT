package com.pilot.cwt.repository;

import com.pilot.cwt.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JPATokenRepository extends JpaRepository<Token, String> {

//    @Query(value = "SELECT tok FROM token t WHERE t.email =:email", nativeQuery = true)
    Token findByEmail(String email);

}
