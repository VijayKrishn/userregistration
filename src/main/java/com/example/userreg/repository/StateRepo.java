package com.example.userreg.repository;

import com.example.userreg.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepo extends JpaRepository<State, Integer> {

    public List<State> findByCountryId(Integer countryId);
}
