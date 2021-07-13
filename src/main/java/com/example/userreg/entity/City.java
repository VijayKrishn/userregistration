package com.example.userreg.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "CITIES_MASTER")
public class City {

    @Id
    @GeneratedValue
    @Column(name = "CITY_ID")
    private Integer cityId;

    @Column(name = "CITY_NAME")
    private String cityName;

    @Column(name = "STATE_ID")
    private Integer stateId;
}
