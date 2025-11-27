package com.self.SpringJDBCdemo.dto;

import com.self.SpringJDBCdemo.model.OrderJPA;
import jakarta.persistence.ManyToMany;

import java.util.List;

public class ProductResponseDTO {
    private int id;

    private String name;

    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
