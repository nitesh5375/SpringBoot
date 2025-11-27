package com.self.SpringJDBCdemo.controller;


import com.self.SpringJDBCdemo.dto.AddItemRequestDTO;
import com.self.SpringJDBCdemo.dto.OrderItemDTO;
import com.self.SpringJDBCdemo.dto.OrderRequestDTO;
import com.self.SpringJDBCdemo.dto.OrderResponseDTO;
import com.self.SpringJDBCdemo.model.OrderJPA;
import com.self.SpringJDBCdemo.service.OrderJPAService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderJPAController {

    @Autowired
    private OrderJPAService orderService;



    @PostMapping("/order/{orderId}/items")
    public OrderItemDTO addItem(@PathVariable int orderId,
                                @RequestBody @Valid AddItemRequestDTO req){
        return orderService.addItem(orderId, req);
    }

    @GetMapping("/order/{orderId}")
    public OrderResponseDTO getOrder(@PathVariable int orderId){
        return orderService.getOrderWithItems(orderId);
    }


    @PostMapping("/order/user/{user_id}")
    public OrderResponseDTO createOrder(@PathVariable int user_id, @RequestBody @Valid OrderRequestDTO request){
        return orderService.createOrder(user_id, request);
    }

    @GetMapping("/orders/user/{userId}/orders/paged")
    public Page<OrderResponseDTO> getUserOrders(@PathVariable int userId, @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy){
        return  orderService.getOrdersByUser(userId, page, size, sortBy);
    }

    @PostMapping("/order/{orderId}/addProduct/{productId}")
    public OrderResponseDTO addProduct(@PathVariable int orderId, @PathVariable int productId){
        return orderService.addProductToOrder(orderId, productId);
    }


}
