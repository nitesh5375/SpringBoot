package com.self.SpringJDBCdemo.service;

import com.self.SpringJDBCdemo.exception.IdNotFoundException;
import com.self.SpringJDBCdemo.exception.UserNotFoundException;
import com.self.SpringJDBCdemo.model.Order;
import com.self.SpringJDBCdemo.model.UserJPA;
import com.self.SpringJDBCdemo.repository.OrderJPARepository;
import com.self.SpringJDBCdemo.repository.UserJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderJPARepository orderJPARepository;

    @Autowired
    private UserJPARepository userJPARepository;

    public Order addOrder(int user_id, String desc){
        UserJPA user = userJPARepository.findById(user_id).orElseThrow(() -> new UserNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setDescription(desc);

        orderJPARepository.save(order);

        return order;
    }

    public Order updateOrderById(int id, String newDesc) {
        Order order = orderJPARepository.findById(id).orElseThrow(() ->  new IdNotFoundException("Id not found"));
        order.setDescription(newDesc);
        orderJPARepository.save(order);

        return order;
    }
}
