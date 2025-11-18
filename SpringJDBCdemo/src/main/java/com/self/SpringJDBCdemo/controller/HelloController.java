package com.self.SpringJDBCdemo.controller;

import com.self.SpringJDBCdemo.model.Users;
import com.self.SpringJDBCdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class HelloController {

@Autowired
UserService userService;

    @GetMapping("/hello")
    String hello(){
        return "Hello Spring Boot";
    }

    @GetMapping("/hello/{name}")
    String hello(@PathVariable String name){
        return "Hello: "+name;
    }

    @PostMapping("/greet")
    String greet(@RequestBody Users data){
   //{
        //  "name": "Nitesh",
        //  "age": 25
        //}

        //Spring Boot automatically converts it to:
        //
        //Users data = new Users();
        //data.setName("Nitesh");
        //data.setAge(25);
        return userService.createGreeting(data);
    }

    @GetMapping("/users")
    List<Users> fetchAllUsers(){
        return userService.fetchAllUsers();
    }
}
