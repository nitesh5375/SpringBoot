package com.self.SpringJDBCdemo.repository;

import com.self.SpringJDBCdemo.model.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface UserJPARepository extends JpaRepository<UserJPA,Integer> {

    }