package com.self.SpringJDBCdemo.service;

import com.self.SpringJDBCdemo.dto.AddItemRequestDTO;
import com.self.SpringJDBCdemo.dto.OrderItemDTO;
import com.self.SpringJDBCdemo.dto.OrderRequestDTO;
import com.self.SpringJDBCdemo.dto.OrderResponseDTO;
import com.self.SpringJDBCdemo.exception.IdNotFoundException;
import com.self.SpringJDBCdemo.exception.UserNotFoundException;
import com.self.SpringJDBCdemo.model.OrderItemJPA;
import com.self.SpringJDBCdemo.model.OrderJPA;
import com.self.SpringJDBCdemo.model.Product;
import com.self.SpringJDBCdemo.model.UserJPA;
import com.self.SpringJDBCdemo.repository.OrderItemRepository;
import com.self.SpringJDBCdemo.repository.OrderJPARepository;
import com.self.SpringJDBCdemo.repository.ProductRepository;
import com.self.SpringJDBCdemo.repository.UserJPARepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderJPAService {
    @Autowired private OrderJPARepository orderRepo;
    @Autowired private UserJPARepository userRepo;
    @Autowired private OrderItemRepository itemRepo;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ProductRepository productRepo;

    public OrderResponseDTO createOrder(int userId, OrderRequestDTO request){
        UserJPA user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        OrderJPA order = new OrderJPA();
        order.setDescription(request.getDescription());
        order.setUser(user);

        OrderJPA saved = orderRepo.save(order); // cascade will persist items if present
        OrderResponseDTO dto = modelMapper.map(saved, OrderResponseDTO.class);
        dto.setItems(saved.getItems()
                .stream()
                .map(it -> modelMapper.map(it, OrderItemDTO.class))
                .collect(Collectors.toList()));
        dto.setUserId(user.getId());
        return dto;
    }

    public OrderItemDTO addItem(int orderId, AddItemRequestDTO req){
        OrderJPA order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IdNotFoundException("Order id not found"));

        OrderItemJPA item = new OrderItemJPA();
        item.setProductName(req.getProductName());
        item.setQuantity(req.getQuantity());
        item.setPrice(req.getPrice());

        // maintain both sides
        order.addItem(item);

        orderRepo.save(order); // cascade persist item
        return modelMapper.map(item, OrderItemDTO.class);
    }

    public OrderResponseDTO getOrderWithItems(int orderId){
        // avoid N+1 by using fetch join (see below) OR @EntityGraph in repo
        OrderJPA order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IdNotFoundException("Order id not found"));

        OrderResponseDTO dto = modelMapper.map(order, OrderResponseDTO.class);
        dto.setItems(order.getItems()
                .stream()
                .map(it -> modelMapper.map(it, OrderItemDTO.class))
                .collect(Collectors.toList()));
        dto.setUserId(order.getUser().getId());
        return dto;
    }

    public Page<OrderResponseDTO> getOrdersByUser(int userId, int page, int size, String sortBy) {
        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by(sortBy));
        Page<OrderJPA> result = (Page<OrderJPA>) orderRepo.findByUserId(userId, pageable);

        return result.map(it -> modelMapper.map(it, OrderResponseDTO.class));
    }

    public OrderResponseDTO addProductToOrder(int orderId, int productId){
        OrderJPA order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IdNotFoundException("Order id not found"));

        Product product = productRepo.findById(productId).orElseThrow(() -> new IdNotFoundException("Product id not found"));

        order.getProducts().add(product);       // since getProducts() returns a List, we can perform add operation on it.
        orderRepo.save(order);

        return modelMapper.map(order, OrderResponseDTO.class);
    }


}
