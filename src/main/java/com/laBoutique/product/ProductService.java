package com.laBoutique.product;




import java.time.Clock;
import java.time.Instant;
import java.util.List;

import com.laBoutique.product.Exception.BadRequestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private  final Clock clock;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper, Clock clock) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.clock = clock;
    }


    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public void save(ProductDTO product) {
        if(product.getPrice() <0){
            throw new BadRequestException("price should NOT be negative");
        }
        Boolean exists = productRepository.existsByReference(product.getReference());
        if(exists){
            throw  new BadRequestException("reference "+product.getReference()+" exists");
        }

        Product product1 = productMapper.productDTOtoProduct(product);
        String slug = product.getName().toLowerCase().replace(" ","-");
        product1.setSlug(slug);
        product1.setCreatedBy("ADMIN");
        product1.setModifiedBy("ADMIN");


        product1.setCreatedDate(Instant.now(clock));
        product1.setModifiedDate(Instant.now(clock));
        productRepository.saveAndFlush(product1);
    }
}
