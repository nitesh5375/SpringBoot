package com.self.SpringJDBCdemo.controller;


import com.self.SpringJDBCdemo.dto.AddItemRequestDTO;
import com.self.SpringJDBCdemo.dto.OrderItemDTO;
import com.self.SpringJDBCdemo.dto.OrderRequestDTO;
import com.self.SpringJDBCdemo.dto.OrderResponseDTO;
import com.self.SpringJDBCdemo.service.OrderJPAService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
public class OrderJPAController {

    @Autowired
    private OrderJPAService orderService;

    @GetMapping("/ping")
    public String ping(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails.getUsername());
        return "pong";
    }

    /* ---------------------------------------
       CREATE ORDER (Authenticated user only)
       --------------------------------------- */
    @PostMapping
    public OrderResponseDTO createOrder(
            @RequestBody @Valid OrderRequestDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return orderService.createOrder(userDetails.getUsername(), request);
    }




    /* ---------------------------------------
       GET SINGLE ORDER (Ownership enforced)
       --------------------------------------- */
    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrder(
            @PathVariable int orderId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        System.out.println("inside getOrder and will fetch order details");
        return orderService.getOrderForUser(orderId, userDetails.getUsername());
    }

    /* ---------------------------------------
       ADD ITEM TO ORDER
       --------------------------------------- */
    @PostMapping("/{orderId}/items")
    public OrderItemDTO addItem(
            @PathVariable int orderId,
            @RequestBody @Valid AddItemRequestDTO req,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return orderService.addItem(orderId, req, userDetails.getUsername());
    }

    /* ---------------------------------------
       GET USER ORDERS (Paged)
       --------------------------------------- */
    @GetMapping("/my")
    public Page<OrderResponseDTO> getMyOrders(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return orderService.getOrdersByUsername(
                userDetails.getUsername(), page, size, sortBy);
    }

    /* ---------------------------------------
       ADD PRODUCT TO ORDER
       --------------------------------------- */
    @PostMapping("/{orderId}/addProduct/{productId}")
    public OrderResponseDTO addProduct(
            @PathVariable int orderId,
            @PathVariable int productId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return orderService.addProductToOrder(
                orderId, productId, userDetails.getUsername());
    }
}

