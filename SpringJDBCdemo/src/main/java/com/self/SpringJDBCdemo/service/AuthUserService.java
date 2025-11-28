package com.self.SpringJDBCdemo.service;

import com.self.SpringJDBCdemo.model.AuthUser;
import com.self.SpringJDBCdemo.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

    @Autowired
    public AuthUserRepository authUserRepository;

    public void registerUser(AuthUser user) {
        authUserRepository.save(user);
    }
}
