package com.example.userreg.binding;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserForm {

    private String fname;
    private String lname;
    private String email;
    private Long phno;
    private LocalDate dob;
    private String gender;
    private Integer countryId;
    private Integer stateId;
    private Integer cityId;

}
