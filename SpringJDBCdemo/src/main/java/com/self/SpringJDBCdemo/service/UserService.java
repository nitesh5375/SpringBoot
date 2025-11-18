package com.self.SpringJDBCdemo.service;

import com.self.SpringJDBCdemo.model.Users;
import com.self.SpringJDBCdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String createGreeting(Users data) {
//        return "hello " + data.getName() + ", your age is " + data.getAge();

        userRepository.saveUser(data);

        return "User Created Successfully";
    }

    public List<Users> fetchAllUsers() {
        return userRepository.fetchAllUsers();
    }
}
