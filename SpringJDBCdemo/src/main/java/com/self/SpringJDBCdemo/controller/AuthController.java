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
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/auth/register")
    public String register(@RequestBody AuthUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authUserService.registerUser(user);

        return "User registered successfully";

    }

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        System.out.println("Entered password: " + request.getPassword());

        authUserService.authUserRepository.findByUsername(request.getUsername())
                .ifPresent(u -> {
                    System.out.println("Stored hash: " + u.getPassword());
                    System.out.println("Matches? " + passwordEncoder.matches(request.getPassword(), u.getPassword()));
                });

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

        String token = jwtUtil.generateToken(request.getUsername());

        return new LoginResponse(
                token,
                request.getUsername(),
                "Login successful"
        );

    } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Authentication failed: " + e.getMessage());
    }
    }
}
