package com.self.SpringJDBCdemo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//@Entity → tells JPA/Hibernate that the class represents a database entity (table).
//@Entity also tells Hibernate to create a table if using auto DDL (spring.jpa.hibernate.ddl-auto=create/update).
@Entity
@Table(name = "users")  //If @Table is not used, JPA will use the class name as the table name.
public class UserJPA {

    @Id         // This annotations indicates to hibernate that it is a primary key
    //@Id marks the primary key field, which is required for every entity so Hibernate can uniquely identify records.
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //@GeneratedValue means “Do NOT expect the application to set the id. Generate it automatically.”
    //IDENTITY means:
    //✔ The database generates the primary key
    //✔ Using AUTO_INCREMENT
    private int id;

//    @NotNull(message = "Name can not be empty")       //NotNull is fine but it accepts ""  value which is not desired.
//    @NotEmpty(message = "Name can not be empty")
//        @Size(min =1, max =50, message = "Name must be between 1 to 50 characters")
    private String name;

//    @Min(value =18, message = "user must be above 18")
//    @Max(value = 60, message = "User must be below 60")
    private int age;

    //the owner of the relationship is usually the side with @ManyToOne.
    //@OneToMany side often uses mappedBy to point back to the owning side.
    //@OneToMany is used on the parent side and represents a collection of child records.
    @OneToMany(mappedBy = "user", //It tells Hibernate that the foreign key for this relationship is NOT in this entity (UserJPA), but in the child entity (OrderJPA) inside the field named 'user'.
            cascade = CascadeType.ALL,  //cascade = CascadeType.ALL tells Hibernate: “Whenever you perform an operation on the parent, automatically apply the same operation to the children.”
            fetch = FetchType.LAZY)  //user = { id=1, name="Alex", age=25 }
                                    // orders = LAZY proxy (empty placeholder)

    private List<OrderJPA> orders =  new ArrayList<>();
    //The list exists only in memory, not in the database.
    //Its purpose is to allow:
    //✔ Easy navigation from parent → children
    //✔ Easy JSON creation (for REST APIs)
    //✔ Easy cascade save/update/delete
    //✔ Easy business logic operations

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

//When building REST API responses (DTO mapping, JSON conversion), Then Jackson or ModelMapper will call: user.getOrders()
    //getOrders() is the ONLY way Hibernate, Jackson, ModelMapper, and your own code can read the child collection of a User
    public List<OrderJPA> getOrders() {
        return orders;
    }

    // it is used when automatic cascading has to happen like insert, update, delete
    public void setOrders(List<OrderJPA> users) {
        this.orders = users;
    }
}
