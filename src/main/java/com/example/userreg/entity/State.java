package com.example.userreg.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "STATES_MASTER")
public class State {

    @Id
    @GeneratedValue
    @Column(name = "STATE_ID")
    private Integer stateId;

    @Column(name = "COUNTRY_ID")
    private Integer countryId;

    @Column(name = "STATE_NAME")
    private String stateName;
}
