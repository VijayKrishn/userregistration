package com.example.userreg.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "COUNTRY_MASTER")
public class Country {

    @Id
    @GeneratedValue
    @Column(name = "COUNTRY_ID")
    private Integer countryId;

    @Column(name = "COUNTRY_CODE")
    private String countryCode;

    @Column(name = "COUNTRY_NAME")
    private String countryName;
}
