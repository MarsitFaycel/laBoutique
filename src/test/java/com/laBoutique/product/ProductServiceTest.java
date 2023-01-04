package com.laBoutique.product;

import com.laBoutique.product.Exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService underTestproductService;


    @BeforeEach
    void setUp() {

        underTestproductService= new ProductService(productRepository);
    }



    @Test
    void getAllProduct() {
        //when
        underTestproductService.getAllProduct();
        //then
        verify(productRepository).findAll();
    }

    @Test
    void save() {
        //given
        Product product = new Product();
        product.setName("product1");
        product.setReference("product-1");
        product.setPrice(12.5f);

        //when
        underTestproductService.save(product);

        //then

        ArgumentCaptor<Product> productArgumentCaptor =ArgumentCaptor.forClass(Product.class);

        verify(productRepository).saveAndFlush(productArgumentCaptor.capture());
        Product product1 = productArgumentCaptor.getValue();
        assertThat(product1).isEqualTo(product);
    }

    @Test
    void saveProductwithNegatifPrice() {
        //given
        Product product = new Product();
        product.setName("product1");
        product.setReference("product-1");
        product.setPrice(-12.5f);

        //when

        //then
        assertThatThrownBy(()->underTestproductService.save(product))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("price should NOT be negative");

    }

    @Test
    void saveProductwithExistingReference() {
        //given
        Product productExists = new Product();
        productExists.setName("product1");
        productExists.setReference("product-1");
        productExists.setPrice(12.5f);

       given(productRepository.existsByReference(productExists.getReference()))
               .willReturn(true);

        //when

        //then
        assertThatThrownBy(()->underTestproductService.save(productExists))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("reference "+productExists.getReference()+" exists");

    }
}