package com.self.SpringJDBCdemo.service;


import com.self.SpringJDBCdemo.dto.UserRequestDTO;
import com.self.SpringJDBCdemo.dto.UserResponseDTO;
import com.self.SpringJDBCdemo.exception.UserNotFoundException;
import com.self.SpringJDBCdemo.model.UserJPA;
import com.self.SpringJDBCdemo.repository.UserJPARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserJPAService {

    @Autowired
    private UserJPARepository userJPARepository;

    @Autowired
    ModelMapper modelMapper;

    public UserResponseDTO createUser(UserRequestDTO data) {
        UserJPA entity = modelMapper.map(data, UserJPA.class);
        UserJPA savedEntity = userJPARepository.save(entity);
        return modelMapper.map(savedEntity, UserResponseDTO.class);
    }

    public List<UserResponseDTO> fetchAllUser(){
        return userJPARepository.findAll().stream().map(user -> modelMapper.map(user, UserResponseDTO.class)).toList();
    }

    public UserResponseDTO findUserById(int id){
        UserJPA entity = userJPARepository.findById(id).orElseThrow(() -> new UserNotFoundException("No record found with id " + id));
        return modelMapper.map(entity, UserResponseDTO.class);
    }

    public UserResponseDTO updateUser(int id, UserRequestDTO data){

       UserJPA entity = userJPARepository.findById(id).orElseThrow(() -> new UserNotFoundException("No record found with id " + id));
       modelMapper.map(data,entity);
       UserJPA savedEntity = userJPARepository.save(entity);
       return modelMapper.map(savedEntity, UserResponseDTO.class);
    }

    public UserResponseDTO deleteUser(int id) {
       UserJPA entity = userJPARepository.findById(id).orElseThrow(() -> new UserNotFoundException("No record found with id " + id));

       userJPARepository.deleteById(id);

       return modelMapper.map(entity, UserResponseDTO.class);
    }


//
//    public UserJPA createUser(UserJPA data) {
//        return userJPARepository.save(data);
//    }
//
//    public List<UserJPA> fetchAllUser(){
//        return  userJPARepository.findAll();
//    }
//
//    public UserJPA findUserById(int id){
//        return userJPARepository.findById(id).orElseThrow(() -> new UserNotFoundException("No record found with id " + id));
//    }
//
//    public String updateUser(int id, UserJPA data){
//        if(userJPARepository.existsById(id)){
//            data.setId(id);
//            userJPARepository.save(data);
//            return "user updated successfully";
//        }
//        else {
//            throw new UserNotFoundException("Update command failed");
//        }
//    }
//
//    public String deleteUser(int id) {
//        if(!userJPARepository.existsById(id)) {
//            throw new UserNotFoundException("Delete command failed");
//        }
//        userJPARepository.deleteById(id);
//        return "User deleted successfully";
//    }
//


}
