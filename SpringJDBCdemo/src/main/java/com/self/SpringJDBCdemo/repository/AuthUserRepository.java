package com.self.SpringJDBCdemo.repository;

import com.self.SpringJDBCdemo.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {

    Optional<AuthUser> findByUsername(String username);
}
