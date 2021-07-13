package com.example.userreg.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "USER_ACCOUNT")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "FIRST_NAME")
    private String fname;

    @Column(name = "LAST_NAME")
    private String lname;

    @Column(name = "USER_EMAIL", unique = true)
    private String email;

    @Column(name = "USER_PWD")
    private String usrPwd;

    @Column(name = "USER_MOBILE")
    private Long phno;

    @Column(name = "DOB")
    private LocalDate dob;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "COUNTRY_ID")
    private Integer countryId;

    @Column(name = "STATE_ID")
    private Integer stateId;

    @Column(name = "CITY_ID")
    private Integer cityId;

    @Column(name = "ACC_STATUS")
    private String accStatus;

    @Column(name = "CREATED_DATE", updatable = false)
    @CreationTimestamp
    private LocalDate createdDate;

    @Column(name = "UPDATED_DATE", insertable = true)
    @UpdateTimestamp
    private LocalDate updatedDate;
}
