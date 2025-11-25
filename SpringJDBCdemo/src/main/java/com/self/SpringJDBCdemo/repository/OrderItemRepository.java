package com.self.SpringJDBCdemo.repository;

import com.self.SpringJDBCdemo.model.OrderItemJPA;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItemJPA, Integer> {}

