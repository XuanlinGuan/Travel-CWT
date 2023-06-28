package com.pilot.customersystem.controller;

import com.pilot.customersystem.entity.Customer;
import com.pilot.customersystem.entity.Login;
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

    /**
    * @Description: Welcome
    * @Param: []
    * @return: java.lang.String
    * @Author: Xuanlin Guan
    */
    @GetMapping(value = "/customersystem/index")
    public String start() {
        return "Welcome to TRAVEl-CWT System";
    }

    /**
    * @Description: Login request
    * @Param: [login]
    * @return: java.lang.String
    * @Author: Xuanlin Guan
    */
    @PostMapping(value = "/customersystem/login")
    public String login(@RequestBody Login login) {
        return loginService.sendLoginInfo(login);
    }
}
