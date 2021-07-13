package com.example.userreg.controller;

import com.example.userreg.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgotPwdRestController {

    @Autowired
    private UserServiceImpl service;

    @GetMapping("/forgotPwd/{emailId}")
    public String forgotPwd(@PathVariable String emailId) {
        boolean status = service.forgotPwd(emailId);
        if(status)
            return "We have sent password to your email";
        return "Please enter valid email id";
    }
}
