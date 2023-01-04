package com.laBoutique.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO  {

    private Long id;
    private String name;
    private String reference;
    private float price;

    public  ProductDTO(Product product){
        this.id= product.getId();
        this.name=product.getName();
        this.price=product.getPrice();
        this.reference=product.getReference();

    }


}
