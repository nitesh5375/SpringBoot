package com.self.SpringJDBCdemo.service;

import com.self.SpringJDBCdemo.model.AuthUser;
import com.self.SpringJDBCdemo.repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthUserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AuthUser user = authUserRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())  // ROLE_USER or ROLE_ADMIN
                .build();
    }

}
