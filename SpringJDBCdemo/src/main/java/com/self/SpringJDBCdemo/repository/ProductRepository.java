package com.self.SpringJDBCdemo.repository;

import com.self.SpringJDBCdemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
