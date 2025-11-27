package com.self.SpringJDBCdemo.controller;


import com.self.SpringJDBCdemo.dto.OrderRequestDTO;
import com.self.SpringJDBCdemo.dto.OrderResponseDTO;
import com.self.SpringJDBCdemo.dto.UserRequestDTO;
import com.self.SpringJDBCdemo.dto.UserResponseDTO;
import com.self.SpringJDBCdemo.exception.UserNotFoundException;
import com.self.SpringJDBCdemo.model.UserJPA;
import com.self.SpringJDBCdemo.service.UserJPAService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//

@RestController
public class UserJPAController {

    @Autowired
    UserJPAService userJPAService;

    @GetMapping("/userJPA")
    public List<UserResponseDTO> getAllUserJPA(){
        return userJPAService.fetchAllUser();
    }

    @GetMapping("/userJPA/{id}")
    public UserResponseDTO getUserJPAByID(@PathVariable int id){

        return userJPAService.findUserById(id);
    }

    @PostMapping("/userJPA")
    public UserResponseDTO createUserJPA(@Valid @RequestBody UserRequestDTO data) throws UserNotFoundException {
        return userJPAService.createUser(data);
    }

    @PutMapping("/userJPA/{id}")
    public UserResponseDTO updateUserJPA(@Valid @RequestBody UserRequestDTO data, @PathVariable int id){
        return userJPAService.updateUser(id,data);
    }

    @DeleteMapping("/userJPA/{id}")
    public UserResponseDTO deleteUserJPA(@PathVariable int id){
        return userJPAService.deleteUser(id);
    }


    @GetMapping("/users/search")
    public UserResponseDTO searchUserJPA(@Valid @RequestParam(name = "name") String name){
        return userJPAService.searchUser(name);
    }


    @GetMapping("/users/searchNameContaining")
    public UserResponseDTO searchUserNameJPA(@Valid @RequestParam(name = "name") String name){
        return userJPAService.searchUserContainingName(name);
    }

    @GetMapping("users/searchByAge")
    public List<UserResponseDTO> searchbyAgeBetween(@Valid @RequestParam int minAge, @Valid @RequestParam int maxAge){
        return userJPAService.findByAgeBetween(minAge,maxAge);
    }

    @GetMapping("/users/searchAboveAge")
    public List<UserResponseDTO> searchAboveAge(@Valid @RequestParam(name = "age") int ageLimit){
        return userJPAService.ageGreaterThan(ageLimit);
    }

//    @PostMapping("/userJPA")
//    public UserJPA createUserJPA(@Valid @RequestBody UserJPA data) throws UserNotFoundException {
//        return userJPAService.createUser(data);
//    }
//
//    @GetMapping("/userJPA")
//    public List<UserJPA> getAllUserJPA(){
//        return userJPAService.fetchAllUser();
//    }
//
//    @GetMapping("/userJPA/{id}")
//    public UserJPA getAllUserJPA(@PathVariable int id){
//        return userJPAService.findUserById(id);
//    }
//
//    @PutMapping("/userJPA/{id}")
//    public String updateUserJPA(@Valid @RequestBody UserJPA data, @PathVariable int id){
//        return userJPAService.updateUser(id,data);
//    }
//
//    @DeleteMapping("/userJPA/{id}")
//    public String deleteUserJPA(@PathVariable int id){
//        return userJPAService.deleteUser(id);
//    }




}
