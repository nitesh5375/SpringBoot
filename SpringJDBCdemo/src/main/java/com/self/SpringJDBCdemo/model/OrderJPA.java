package com.self.SpringJDBCdemo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")       // creating a column in orders table
    private UserJPA user;       //FK to primary key of UserJPA table

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order",
    cascade = CascadeType.ALL,  //cascade = CascadeType.ALL tells Hibernate: “Whenever you perform an operation on the parent, automatically apply the same operation to the children.”
            orphanRemoval = true // it means if parent entry is deleted, then associated FK items in child will also get deleted.
    )
    private List<OrderItemJPA> items = new ArrayList<>();

    public List<OrderItemJPA> getItems() {
        return items;
    }

    public void setItems(List<OrderItemJPA> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserJPA getUser() {
        return user;
    }

    public void setUser(UserJPA user) {
        this.user = user;
    }

    public List<OrderItemJPA> getOrderItems() {
        return items;
    }

    public void setOrder(List<OrderItemJPA> orderItems) {
        this.items = orderItems;
    }

    public void addItem(OrderItemJPA item){
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItemJPA item){
        items.remove(item);
        item.setOrder(null);
    }
}
