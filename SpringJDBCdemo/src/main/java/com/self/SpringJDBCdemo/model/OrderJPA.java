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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)  //"Load only when needed" //proxies (fake placeholder objects). // will only be invoked when user.getUser() is called
    @JoinColumn(name = "user_id", nullable = false)       // creating a column in orders table //JPA ALWAYS maps foreign keys to the primary key of the target entity unless otherwise stated using @MapsId
    private UserJPA user;       //FK to primary key of UserJPA table

    //Default behavior differs by relation type:
    //
    //@ManyToOne → EAGER by default
    //
    //@OneToOne → EAGER by default
    //
    //@OneToMany → LAZY by default
    //
    //@ManyToMany → LAZY by default

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "order", //It tells Hibernate that the foreign key for this relationship is NOT in this entity (OrderJPA), but in the child entity (OrderItemJPA) inside the field named 'order'.
    cascade = CascadeType.ALL,  //cascade = CascadeType.ALL tells Hibernate: “Whenever you perform an operation on the parent, automatically apply the same operation to the children.”
            orphanRemoval = true // it means if parent entry is deleted, then associated FK items in child will also get deleted.
    )
    private List<OrderItemJPA> items = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    public List<OrderItemJPA> getItems() {
        return items;
    }

    public void setItems(List<OrderItemJPA> items) {
        this.items = items;
    }

    public void addItem(OrderItemJPA item){
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItemJPA item){
        items.remove(item);
        item.setOrder(null);
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



    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }


}
