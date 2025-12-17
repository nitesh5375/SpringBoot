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
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.self.SpringJDBCdemo.dto.CreateOrderDTO;
import com.self.SpringJDBCdemo.dto.CreateOrderItemDTO;

import java.util.stream.Collectors;


@Service
public class OrderJPAService {
    @Autowired private OrderJPARepository orderRepo;
    @Autowired private UserJPARepository userRepo;
    @Autowired private OrderItemRepository itemRepo;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ProductRepository productRepo;


    @Transactional
    // @Transactional ensures:
    // If save() fails → nothing is committed
    // If exception occurs → auto rollback
    // method access type must be public
    //DB modifying methods need @Transactional annotation
    public OrderResponseDTO createOrder(String username, OrderRequestDTO request) {

        UserJPA user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        OrderJPA order = new OrderJPA();
        order.setDescription(request.getDescription());
        order.setUser(user);

        orderRepo.save(order);

        return modelMapper.map(order, OrderResponseDTO.class);
    }

    @Transactional
    public OrderItemDTO addItem(int orderId, AddItemRequestDTO req, String username) {

        OrderJPA order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IdNotFoundException("Order not found"));

        if (!order.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You do not own this order");
        }

        OrderItemJPA item = new OrderItemJPA();
        item.setProductName(req.getProductName());
        item.setQuantity(req.getQuantity());
        item.setPrice(req.getPrice());

        order.addItem(item);
        orderRepo.save(order);

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

    public Page<OrderResponseDTO> getOrdersByUsername(
            String username,
            int page,
            int size,
            String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<OrderJPA> result =
                orderRepo.findByUser_Username(username, pageable);

        return result.map(o -> modelMapper.map(o, OrderResponseDTO.class));
    }


    @Transactional
    public OrderResponseDTO addProductToOrder(
            int orderId,
            int productId,
            String username
    ) {
        OrderJPA order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IdNotFoundException("Order not found"));

        if (!order.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You do not own this order");
        }

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IdNotFoundException("Product not found"));

        order.getProducts().add(product);

        return modelMapper.map(order, OrderResponseDTO.class);
    }


    @Transactional
    //When a method annotated with `@Transactional` is called:
    // 1\*Before method execution\*\* - Spring opens a database transaction - Gets a DB connection - Sets `autoCommit = false`
    // 2. \*\*Method executes\*\* - All repository / JDBC / JPA operations run in the same transaction
    // 3. \*\*If method completes normally\*\* - Spring \*\*commits\*\* the transaction
    // 4. \*\*If an exception occurs\*\* - Spring \*\*rolls back\*\* the transaction (default rules below)
    // 5. \*\*Finally\*\* - Connection is returned to the pool
    //By default:
    //✅ Spring rolls back unchecked exceptions
    //RuntimeException    //Error
    //❌ Spring does NOT roll back checked exceptions
    //Exception    //IOException    //ParseException    //SQLException

    //@Transactional(rollbackfor = Exception.class)
    //It overrides the default and tells Spring:
    //If ANY exception happens (checked or unchecked), rollback the transaction.

    public OrderResponseDTO createFullOrder(CreateOrderDTO dto) {

        UserJPA user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        OrderJPA order = new OrderJPA();
        order.setDescription(dto.getDescription());
        order.setUser(user);

        // save order first
        orderRepo.save(order);

        // now add items
        for(CreateOrderItemDTO itemDTO : dto.getItems()) {
            OrderItemJPA item = new OrderItemJPA();
            item.setProductName(itemDTO.getProductName());
            item.setQuantity(itemDTO.getQty());
            item.setOrder(order);

            itemRepo.save(item);
        }

        return modelMapper.map(order, OrderResponseDTO.class);
    }


    public OrderResponseDTO getOrderForUser(int orderId, String username) {
        OrderJPA order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You do not own this order");
        }

        return modelMapper.map(order,  OrderResponseDTO.class);
    }





}
