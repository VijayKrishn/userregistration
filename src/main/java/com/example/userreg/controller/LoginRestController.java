package com.example.userreg.controller;

import com.example.userreg.binding.LoginForm;
import com.example.userreg.binding.UnlockAccountForm;
import com.example.userreg.constants.AppConstants;
import com.example.userreg.properties.AppProps;
import com.example.userreg.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginRestController {

    @Autowired
    private AppProps props;

    @Autowired
    private UserServiceImpl service;

    @GetMapping("/welcome")
    public ResponseEntity<String> getWelcomeMsg() {
        Map<String, String> msgs = props.getMessages();

        return new ResponseEntity<String>(msgs.get(AppConstants.WELCOME_MSG), HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm) {
        String loginCheck = service.loginCheck(loginForm);
        return loginCheck;
    }

    @PostMapping("/unlock")
    public String unlockUserAccount(@RequestBody UnlockAccountForm unlockAccForm) {
        boolean status = service.unlockAccount(unlockAccForm);
        if(status)
            return AppConstants.UNLOCKED;
        return AppConstants.INVALID_CREDENTIALS;
    }

}
