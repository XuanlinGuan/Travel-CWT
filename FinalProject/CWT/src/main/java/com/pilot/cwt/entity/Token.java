package com.pilot.cwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.function.LongFunction;

@Entity
@Table(name = "token")
public class Token {
    @Column(name = "email")
    @Id
    private String email;
    @Column(name = "expiration_timestamp")
    private Date expiration_timestamp;
    @Column(name = "token", length = 500)
    private String token;

    public Token() {

    }
    public Token(String email,String token,Date expiration_timestamp){
        this.email = email;
        this.token = token;
        this.expiration_timestamp = expiration_timestamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpiration_timestamp() {
        return expiration_timestamp;
    }

    public void setExpiration_timestamp(Date expiration_timestamp) {
        this.expiration_timestamp = expiration_timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Token{" +
                "email='" + email + '\'' +
                ", token='" + token + '\'' +
                ", expiration_timestamp=" + expiration_timestamp +
                '}';
    }
}
