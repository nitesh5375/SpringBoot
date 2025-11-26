package com.self.SpringJDBCdemo.repository;

import com.self.SpringJDBCdemo.model.OrderJPA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJPARepository extends JpaRepository<OrderJPA,Integer> {
    List<OrderJPA> findByUserId(int userId);
}
