package com.self.SpringJDBCdemo.service;

import com.self.SpringJDBCdemo.dto.LoginRequest;
import com.self.SpringJDBCdemo.dto.LoginResponse;
import com.self.SpringJDBCdemo.model.AuthUser;
import com.self.SpringJDBCdemo.model.UserJPA;
import com.self.SpringJDBCdemo.repository.AuthUserRepository;
import com.self.SpringJDBCdemo.repository.UserJPARepository;
import com.self.SpringJDBCdemo.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

    @Autowired
    public AuthUserRepository authUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserJPARepository userJPARepository;

    public void registerUser(AuthUser authUser) {
        authUserRepository.save(authUser);

        UserJPA user = new UserJPA();
        user.setId(authUser.getId());              // 🔥 SAME ID
        user.setUsername(authUser.getUsername());
        user.setName(authUser.getUsername());      // or from request
        user.setAge(0);

        userJPARepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        try {
            //At this point user is authenticated only for this request
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            //Token contains:
            //username //issuedAt //expiry //signature
            String token = jwtUtil.generateToken(request.getUsername());

            return new LoginResponse(
                    token,
                    request.getUsername(),
                    "Login successful"
            );

            //if authentication fails, catch block throws error.
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }
}
