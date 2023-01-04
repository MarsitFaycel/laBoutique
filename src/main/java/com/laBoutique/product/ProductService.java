package com.laBoutique.product;

import com.laBoutique.product.Exception.BadRequestException;
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
        if(product.getPrice() <0){
            throw new BadRequestException("price should NOT be negative");
        }
        Boolean exists = productRepository.existsByReference(product.getReference());
        if(exists){
            throw  new BadRequestException("reference "+product.getReference()+" exists");
        }
        productRepository.saveAndFlush(product);
    }
}
