package com.pilot.concur.controller;

import java.util.ArrayList;
import java.util.Optional;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.pilot.concur.entity.Customer;
import com.pilot.concur.service.ConcurService;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RestControllerAdvice
public class CustomerController {

    /**
    * @Description: Pick up a Service bean from container
    * @Param:
    * @return:
    * @Author: Xuanlin Guan
    */
    @Autowired
    private ConcurService concurService;

    /**
    * @Description: create API to create a new customer
    * @Param: [customer]
    * @return: java.lang.String
    * @Author: Xuanlin Guan
    */
    @PostMapping(value = "/concur/create/customer")
    public String createCustomer( @RequestBody Customer customer) throws HttpClientErrorException {
        if (customer.getId().isEmpty()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400),"Customer ID is Empty!");
        }
        if (customer.getAddress().isEmpty()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400),"Address is Empty!");
        }
        if (customer.getLastName().isEmpty()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400),"LastName is Empty!");
        }
        if (customer.getEmail().isEmpty()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400),"Email is Empty!");
        }
        if (customer.getFirstName().isEmpty()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400),"FirstName is Empty!");
        }
        if (concurService.findByEmail(customer.getEmail()).isPresent()) {
            return "This email has been used!";
        }
        return concurService.saveCustomer(customer);
    }

    /**
    * @Description: Delete a customer from concur db
    * @Param: [id]
    * @return: java.lang.String
    * @Author: Xuanlin Guan
    */
    @DeleteMapping(value = "/concur/delete/customer")
    public String deleteCustomer(@RequestParam(value = "id") String id) {
        if (id.isEmpty()) {
            throw new HttpClientErrorException(HttpStatusCode.valueOf(400),"Customer ID is Empty!");
        }
        return concurService.deleteCustomer(id);
    }

    /**
    * @Description: update a exist customer info
    * @Param: [id, LastName, firstName, email, address]
    * @return: java.util.Optional<com.pilot.concur.entity.Customer>
    * @Author: Xuanlin Guan
    */
    @PutMapping(value = "/concur/update/customer")
    public Optional<Customer> updateCustomer(@RequestParam(value = "id") String id, @RequestParam(value = "LastName", required = false)String LastName,
            @RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "email", required = false)String email,
            @RequestParam(value = "address", required = false) String address) {

        Optional<Customer> theCustomer = concurService.getCustomerById(id);
        if (!theCustomer.isPresent()) return null;

        if (LastName != null) {
            theCustomer.get().setLastName(LastName);
        }
        if (firstName != null) {
            theCustomer.get().setFirstName(firstName);
        }
        if (email != null) {
            theCustomer.get().setEmail(email);
        }
        if (address != null) {
            theCustomer.get().setAddress(address);
        }
        concurService.updateCustomer(theCustomer.get());
        return concurService.getCustomerById(id);
    }

    /**
    * @Description: find a customer from db by customer ID
    * @Param: [id]
    * @return: java.util.Optional<com.pilot.concur.entity.Customer>
    * @Author: Xuanlin Guan
    */
    @GetMapping(value = "/concur/get/customerById/{id}")
    public Optional<Customer> getCustomerById(@PathVariable(value = "id")String id) {
        return concurService.getCustomerById(id);
    }

    /**
    * @Description: find a customer form db by customer email
    * @Param: [email]
    * @return: java.util.Optional<com.pilot.concur.entity.Customer>
    * @Author: Xuanlin Guan
    */
    @GetMapping(value = "/concur/get/customerByEmail")
    public Optional<Customer> getCustomerByEmail(@RequestParam(value = "email")String email) {
        return concurService.findByEmail(email);
    }

    /**
    * @Description: find a customer from db by a token from CWT
    * @Param: [token]
    * @return: com.pilot.concur.entity.Customer
    * @Author: Xuanlin Guan
    */
    @PostMapping(value = "/concur/token")
    public Customer findCustomerInfoByToken(@RequestParam(value = "token") String token) {
        Optional<Customer> customer =  concurService.findByToken(token);
        if (customer.isPresent()) {
            return customer.get();
        }
        return null;
    }

    /**
    * @Description: catch the checked exception from the code in this class and show the message
    * @Param: [e]
    * @return: java.lang.String
    * @Author: Xuanlin Guan
    */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleExceptions(Exception e){
        return "Sorry, there is an issue: " + e.getMessage();
    }
}
