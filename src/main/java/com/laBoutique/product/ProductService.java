package com.laBoutique.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public List<Product> getAllProduct(){
        return this.productRepository.findAll();
    }

    public void save(Product product) {
        productRepository.saveAndFlush(product);
    }
}
