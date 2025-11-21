package com.self.SpringJDBCdemo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDTO {

    @NotBlank(message = "Name can't be empty")
    @Size(min=1,max=50, message = "size between 1 to 50")
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
