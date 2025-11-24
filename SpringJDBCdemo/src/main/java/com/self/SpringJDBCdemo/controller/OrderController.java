package com.self.SpringJDBCdemo.controller;


import com.self.SpringJDBCdemo.model.Order;
import com.self.SpringJDBCdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/user/{userId}/orders")
    public Order addOrder(@PathVariable int userId, @RequestBody String description){
        return orderService.addOrder(userId,description);

    }

    @PutMapping("/user/{userId}/update")
    public Order updateOrder(@RequestBody String newDesc, @PathVariable("userId") int id){
        return orderService.updateOrderById(id,newDesc);
    }

}
