package com.pilot.concur.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name="email")
    private String email;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String firstName;

    @Column(name = "address")
    private String address;

    @Override
    public String toString() {
        StringBuilder customerInfo = new StringBuilder();
        for (int i = 0; i <= 2; i++) {
            customerInfo.append(" ");
        }
        return "Customer{" + "\n" +
                customerInfo + "id='" + id + '\'' + "," + "\n" +
                customerInfo + "lastname='" + lastName + '\'' + "," + "\n" +
                customerInfo + "firstname='" + firstName + '\'' + "," + "\n" +
                customerInfo + "email='" + email + '\'' + "," + "\n" +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
