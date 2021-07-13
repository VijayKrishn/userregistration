package com.example.userreg.repository;

import com.example.userreg.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<City, Integer> {

    public List<City> findByStateId(Integer stateId);
}
