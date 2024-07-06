package com.dradest.backend.product.config;

import com.dradest.backend.product.controller.ProductModelAssembler;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ProductTestConfiguration {

    @Bean
    public ProductModelAssembler productModelAssembler() {
        return new ProductModelAssembler();
    }
}
