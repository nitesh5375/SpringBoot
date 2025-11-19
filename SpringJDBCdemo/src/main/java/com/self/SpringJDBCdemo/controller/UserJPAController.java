package com.self.SpringJDBCdemo.controller;


import com.self.SpringJDBCdemo.model.UserJPA;
import com.self.SpringJDBCdemo.service.UserJPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserJPAController {

    @Autowired
    UserJPAService userJPAService;

    @PostMapping("/userJPA")
    public UserJPA createUserJPA(@RequestBody UserJPA data){
        return userJPAService.createUser(data);
    }

    @GetMapping("/userJPA")
    public List<UserJPA> getAllUserJPA(){
        return userJPAService.fetchAllUser();
    }

    @GetMapping("/userJPA/{id}")
    public UserJPA getAllUserJPA(@PathVariable int id){
        return userJPAService.findUserById(id);
    }

    @PutMapping("/userJPA/{id}")
    public String updateUserJPA(@RequestBody UserJPA data, @PathVariable int id){
        return userJPAService.updateUser(id,data);
    }

    @DeleteMapping("/userJPA/{id}")
    public String deleteUserJPA(@PathVariable int id){
        return userJPAService.deleteUser(id);
    }
}
