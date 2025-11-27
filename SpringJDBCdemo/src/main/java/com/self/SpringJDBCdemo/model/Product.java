package com.self.SpringJDBCdemo.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private double price;

    @ManyToMany(mappedBy = "products")
    private List<OrderJPA> orders;

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

    public List<OrderJPA> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderJPA> orders) {
        this.orders = orders;
    }
}
