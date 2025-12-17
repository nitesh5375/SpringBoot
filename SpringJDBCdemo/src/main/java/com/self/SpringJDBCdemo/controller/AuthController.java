package com.self.SpringJDBCdemo.controller;


import com.self.SpringJDBCdemo.dto.LoginRequest;
import com.self.SpringJDBCdemo.dto.LoginResponse;
import com.self.SpringJDBCdemo.model.AuthUser;
import com.self.SpringJDBCdemo.security.JwtUtil;
import com.self.SpringJDBCdemo.security.SecurityConfig;
import com.self.SpringJDBCdemo.service.AuthUserService;
import com.self.SpringJDBCdemo.service.UserJPAService;
import com.self.SpringJDBCdemo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {




    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthUserService authService;

    //In Spring, every request firstly goes into jwtFilter and sees whether I have to filter it according to 'ShouldNotFilter' method
    // If skipped then request comes and authentication happens in controller class,
    @PostMapping("/auth/register")
    public String register(@RequestBody AuthUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authService.registerUser(user);

        return "User registered successfully";

    }

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
