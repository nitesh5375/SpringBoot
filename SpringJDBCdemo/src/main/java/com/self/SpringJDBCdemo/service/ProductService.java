package com.self.SpringJDBCdemo.service;

import com.self.SpringJDBCdemo.dto.ProductRequestDTO;
import com.self.SpringJDBCdemo.dto.ProductResponseDTO;
import com.self.SpringJDBCdemo.model.Product;
import com.self.SpringJDBCdemo.repository.ProductRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ProductResponseDTO createProduct(@Valid ProductRequestDTO data){
        Product product = new Product();
        product.setName(data.getName());
        product.setPrice(data.getPrice());
        productRepository.save(product);

        return modelMapper.map(product,ProductResponseDTO.class);
    }


}
