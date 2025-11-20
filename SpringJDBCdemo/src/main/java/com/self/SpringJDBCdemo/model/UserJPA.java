package com.self.SpringJDBCdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class UserJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @NotNull(message = "Name can not be empty")       //NotNull is fine but it accepts ""  value which is not desired.
    @NotEmpty(message = "Name can not be empty")
        @Size(min =1, max =50, message = "Name must be between 1 to 50 characters")
    private String name;

    @Min(value =18, message = "user must be above 18")
    @Max(value = 60, message = "User must be below 60")
    private int age;


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

}
