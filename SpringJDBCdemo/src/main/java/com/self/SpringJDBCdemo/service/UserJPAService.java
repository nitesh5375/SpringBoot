package com.self.SpringJDBCdemo.service;


import com.self.SpringJDBCdemo.model.UserJPA;
import com.self.SpringJDBCdemo.repository.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserJPAService {

    @Autowired
    private UserJPARepository userJPARepository;

    public UserJPA createUser(UserJPA data) {
        return userJPARepository.save(data);
    }

    public List<UserJPA> fetchAllUser(){
        return  userJPARepository.findAll();
    }

    public UserJPA findUserById(int id){
        return userJPARepository.findById(id).orElse(null);
    }

    public String updateUser(int id, UserJPA data){
        if(userJPARepository.existsById(id)){
            data.setId(id);
            userJPARepository.save(data);
            return "user updated successfully";
        }
        else {
            return "User not found";
        }
    }

    public String deleteUser(int id) {
        if(!userJPARepository.existsById(id)) {
            return "User not found";
        }
        userJPARepository.deleteById(id);
        return "User deleted successfully";
    }

}
