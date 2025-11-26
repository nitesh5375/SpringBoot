package com.self.SpringJDBCdemo.model;

import jakarta.persistence.*;


@Entity
@Table(name = "order_item")
public class OrderItemJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productName;
    private Integer quantity;
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // creating a column in order_item table
    private OrderJPA order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OrderJPA getOrder() {
        return order;
    }

    public void setOrder(OrderJPA order) {
        this.order = order;
    }


}
