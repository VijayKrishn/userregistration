package com.example.userreg.service;

import com.example.userreg.binding.LoginForm;
import com.example.userreg.binding.UnlockAccountForm;
import com.example.userreg.binding.UserForm;

import java.util.Map;

public interface UserService {

    public String loginCheck(LoginForm loginForm);

    //country Id is required to load the states so taking id and name ans similar for other methods
    public Map<Integer, String> getCountries();

    public Map<Integer, String> getStates(Integer countryId);

    public Map<Integer, String> getCities(Integer stateId);

    public String isEmailUnique(String emailId);

    public boolean saveUser(UserForm usrForm);

    public boolean unlockAccount(UnlockAccountForm unlockForm);

    public boolean forgotPwd(String emailId);
}
