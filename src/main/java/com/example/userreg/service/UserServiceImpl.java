package com.example.userreg.service;

import com.example.userreg.binding.LoginForm;
import com.example.userreg.binding.UnlockAccountForm;
import com.example.userreg.binding.UserForm;
import com.example.userreg.constants.AppConstants;
import com.example.userreg.entity.City;
import com.example.userreg.entity.Country;
import com.example.userreg.entity.State;
import com.example.userreg.entity.User;
import com.example.userreg.properties.AppProps;
import com.example.userreg.repository.CityRepo;
import com.example.userreg.repository.CountryRepo;
import com.example.userreg.repository.StateRepo;
import com.example.userreg.repository.UserRepo;
import com.example.userreg.util.EmailUtils;
import com.example.userreg.util.PwdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private CountryRepo countryRepo;

    @Autowired
    private StateRepo stateRepo;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AppProps appProps;

    @Autowired
    private EmailUtils emailUtils;

    //credentials are valid or not and account is locked or not
    @Override
    public String loginCheck(LoginForm loginForm) {
        String msg;
        User user = userRepo.findByEmailAndUsrPwd(loginForm.getEmail(), loginForm.getPwd());
        if(user != null) {
            String accStatus = user.getAccStatus();
            if(AppConstants.LOCKED.equals(accStatus)) {
                msg = appProps.getMessages().get(AppConstants.ACC_LOCKED);
            } else {
                msg = AppConstants.SUCCESS;
            }
        } else {
            msg = appProps.getMessages().get(AppConstants.INVALID_CREDENTIALS);
        }
        return msg;
    }

    @Override
    public Map<Integer, String> getCountries() {
        List<Country> countries = countryRepo.findAll();
        Map<Integer, String> countryMap = new HashMap<>();
//        for(Country country : list) {
//            countryMap.put(country.getCountryId(), country.getCountryName());
//        }
        countries.forEach(country -> {
            countryMap.put(country.getCountryId(), country.getCountryName());
        });
        return countryMap;
    }

    @Override
    public Map<Integer, String> getStates(Integer countryId) {
        List<State> states = stateRepo.findByCountryId(countryId);
        Map<Integer, String> stateMap = new HashMap<>();
        states.forEach(state -> {
            stateMap.put(state.getStateId(), state.getStateName());
        });
        return stateMap;
    }

    @Override
    public Map<Integer, String> getCities(Integer stateId) {
        List<City> cities = cityRepo.findByStateId(stateId);
        Map<Integer, String> cityMap = new HashMap<>();
        cities.forEach(city -> {
            cityMap.put(city.getStateId(), city.getCityName());
        });
        return cityMap;
    }

    @Override
    public String isEmailUnique(String emailId) {
        User entity = new User();
        entity.setEmail(emailId);
        Example<User> example = Example.of(entity);
        Optional<User> findOne = userRepo.findOne(example);
        if(findOne.isPresent()) {
            return AppConstants.DUPLICATE;
        } else {
            return AppConstants.UNIQUE;
        }
    }

    @Override
    public boolean saveUser(UserForm usrForm) {
        User entity = new User();
        BeanUtils.copyProperties(usrForm, entity);
        entity.setAccStatus(AppConstants.LOCKED);
        String pwd = generateRandomPwd(6);
        entity.setUsrPwd(PwdUtils.encrypt(pwd));
        entity = userRepo.save(entity);
        String body = emailUtils.generateMailBody(entity, pwd);
        String subject = appProps.getMessages().get(AppConstants.UNLOCK_ACC_EMAIL_SUB);
        boolean status = emailUtils.sendEmail(usrForm.getEmail(), "subject", body);
        return entity.getUserId() != null ? true : false;
    }

    @Override
    public boolean unlockAccount(UnlockAccountForm unlockForm) {
        String email = unlockForm.getEmail();
        String tempPwd = unlockForm.getTempPwd();
        User user = userRepo.findByEmailAndUsrPwd(email, tempPwd);
        if(user != null) {
            user.setUsrPwd(unlockForm.getNewPwd());
            user.setAccStatus(AppConstants.UNLOCKED);
            userRepo.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean forgotPwd(String emailId) {
        User entity = new User();
        entity.setEmail(emailId);
        Example<User> example = Example.of(entity);
        Optional<User> findOne = userRepo.findOne(example);
        if(findOne.isPresent()) {
            User usrEntity = findOne.get();
            String body = emailUtils.readForgotPwdEmailBody(usrEntity);
            String subject = appProps.getMessages().get(AppConstants.RECOVER_PWD_EMAIL_SUB);
            emailUtils.sendEmail(entity.getEmail(), subject, body);
            return true;
        } else {
            return false;
        }
    }

    private String generateRandomPwd(int length) {
        String candidateChars = AppConstants.CHARS;
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(candidateChars.charAt(random.nextInt(candidateChars
                    .length())));
        }
        return sb.toString();
    }
}
