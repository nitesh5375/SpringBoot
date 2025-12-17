package com.self.SpringJDBCdemo.dto;

import jakarta.validation.constraints.*;

//Validation should be done ONLY in Request DTOs
//NOT in Response DTOs
//NOT in Entity (Model) classes (in most cases)
public class UserRequestDTO {

    //    @NotNull(message = "Name can not be empty")       //NotNull is fine, but it accepts ""  value which is not desired.
    @NotBlank(message = "Name cannot be empty")
    @Size(max = 50, message = "Name must be at most 50 characters")
    private String name;

    @Min(value = 18, message = "User must be at least 18 years old")
    @Max(value = 60, message = "User must be below 60")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
