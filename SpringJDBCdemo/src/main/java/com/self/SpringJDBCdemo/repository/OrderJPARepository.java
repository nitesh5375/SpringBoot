package com.self.SpringJDBCdemo.repository;

import com.self.SpringJDBCdemo.model.OrderJPA;
import com.self.SpringJDBCdemo.model.UserJPA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderJPARepository extends JpaRepository<OrderJPA,Integer> {
    Page<OrderJPA> findByUser_Id(int userId, Pageable pageable);
    Page<OrderJPA> findByUser_Username(String username, Pageable pageable);
}
