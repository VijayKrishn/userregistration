package com.example.userreg.controller;

import com.example.userreg.binding.UserForm;
import com.example.userreg.constants.AppConstants;
import com.example.userreg.properties.AppProps;
import com.example.userreg.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api("This is AirIndia Distributed Component")
public class RegRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppProps props;

    @GetMapping("/countries")
    public Map<Integer, String> getCountries() {
        return userService.getCountries();
    }

    @GetMapping("/states/{countryId}")
    public Map<Integer, String> getStates(@PathVariable Integer countryId) {
        return userService.getStates(countryId);
    }

    @GetMapping("/cities/{stateId}")
    public Map<Integer, String> getCities(@PathVariable Integer stateId) {
        return userService.getCities(stateId);
    }

    @GetMapping("/emailcheck/{email}")
    public String uniqueEmailCheck(String email) {
        String status = userService.isEmailUnique(email);
        return status;
    }

    @PostMapping(value = "/saveUser",
            consumes = {
                    "application/json"
            },
            produces = {
                "application/json"
            }
    )
    @ApiOperation("Used to collect info from user form and save it")
    public String saveUser(@RequestBody UserForm userRegForm) {
        boolean saveUser = userService.saveUser(userRegForm);
        if(saveUser) {
            return AppConstants.SUCCESS;
        }
        return AppConstants.FAILED;
    }
}
