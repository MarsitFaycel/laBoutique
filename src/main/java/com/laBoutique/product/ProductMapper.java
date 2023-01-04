package com.laBoutique.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
public class ProductMapper {

    public Product productDTOtoProduct(ProductDTO productDTO){
        if(productDTO == null){
            return null;
        }else{
            Product product = new Product();
            product.setPrice(productDTO.getPrice());
            product.setName(productDTO.getName());
            product.setReference(productDTO.getReference());
            product.setId(productDTO.getId());
            return product;
        }
    }
}
