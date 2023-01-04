package com.laBoutique.product;

import com.laBoutique.product.Exception.BadRequestException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private Clock clock;
    private static final ZonedDateTime NOW = ZonedDateTime.of(
            2023,
            6,
            10,
            10,
            25,
            52,
            0,
            ZoneId.of("GMT")
    );

    private ProductService underTestproductService;


    @BeforeEach
    void setUp() {

        ProductMapper productMapper = new ProductMapper();
        underTestproductService = new ProductService(productRepository, productMapper, clock);
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
        ProductDTO product = new ProductDTO();
        product.setName("product 1");
        product.setReference("product 1");
        product.setPrice(12.5f);

        //when
        when(clock.instant()).thenReturn(NOW.toInstant());
        underTestproductService.save(product);

        //then


        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).saveAndFlush(productArgumentCaptor.capture());

        Product product1 = productArgumentCaptor.getValue();
        assertThat(product1.getPrice()).isEqualTo(product.getPrice());
        assertThat(product1.getReference()).isEqualTo(product.getReference());
        assertThat(product1.getName()).isEqualTo(product.getName());
        assertThat(product1.getSlug()).isEqualTo("product-1");
        assertThat(product1.getCreatedDate()).isInstanceOf(Instant.class);
        assertThat(product1.getCreatedDate()).isEqualTo(NOW.toInstant());
        assertThat(product1.getCreatedDate()).isEqualTo(product1.getModifiedDate());

    }

    @Test
    void saveProductwithNegatifPrice() {
        //given
        ProductDTO product = new ProductDTO();
        product.setName("product1");
        product.setReference("product-1");
        product.setPrice(-12.5f);

        //when

        //then
        assertThatThrownBy(() -> underTestproductService.save(product))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("price should NOT be negative");

    }

    @Test
    void saveProductwithExistingReference() {
        //given
        ProductDTO productExists = new ProductDTO();
        productExists.setName("product1");
        productExists.setReference("product-1");
        productExists.setPrice(12.5f);

        given(productRepository.existsByReference(productExists.getReference()))
                .willReturn(true);

        //when

        //then
        assertThatThrownBy(() -> underTestproductService.save(productExists))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("reference " + productExists.getReference() + " exists");

    }
}