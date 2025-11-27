package com.self.SpringJDBCdemo.controller;


import com.self.SpringJDBCdemo.dto.ProductRequestDTO;
import com.self.SpringJDBCdemo.dto.ProductResponseDTO;
import com.self.SpringJDBCdemo.service.OrderJPAService;
import com.self.SpringJDBCdemo.service.ProductService;
import jakarta.validation.Valid;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    OrderJPAService orderService;

    @Autowired private ProductService productService;

    //@Valid tells Spring to perform validation checks on your DTO
    //It ensures your ProductRequestDTO annotations like
    //@NotBlank, @Min, @NotNull actually work
    //Without @Valid, validation annotations are ignored
    @PostMapping("/create/product")
    public ProductResponseDTO addProduct(@Valid @RequestBody ProductRequestDTO product) {
        return productService.createProduct(product);
    }
}
