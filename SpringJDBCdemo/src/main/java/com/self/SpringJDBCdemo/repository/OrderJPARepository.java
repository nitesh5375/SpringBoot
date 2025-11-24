package com.self.SpringJDBCdemo.repository;

import com.self.SpringJDBCdemo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJPARepository extends JpaRepository<Order,Integer> {
}
