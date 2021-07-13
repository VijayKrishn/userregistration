package com.example.userreg.repository;

import com.example.userreg.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    public User findByEmailAndUsrPwd(String email, String pwd);

}
