package com.pilot.customersystem.controller;

import com.pilot.customersystem.entity.Customer;
import com.pilot.customersystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
public class LogInController {
    @Autowired
    LoginService loginService;


    @GetMapping(value = "/customersystem/index")
    public String start() {
        return "Welcome to TRAVEl-CWT System";
    }

    @PostMapping(value = "/customersystem/login")
    public String login(@RequestBody Customer customer) {
        return loginService.sendLoginInfo(customer);
    }
}
