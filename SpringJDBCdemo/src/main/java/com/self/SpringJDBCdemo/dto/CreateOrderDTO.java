package com.self.SpringJDBCdemo.dto;

import java.util.List;

public class CreateOrderDTO {
    private int userId;
    private String description;
    private List<CreateOrderItemDTO> items;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CreateOrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CreateOrderItemDTO> items) {
        this.items = items;
    }
}
