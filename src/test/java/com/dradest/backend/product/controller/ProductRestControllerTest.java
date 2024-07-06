package com.dradest.backend.product.controller;

import com.dradest.backend.product.config.ProductTestConfiguration;
import com.dradest.backend.product.jpa.model.Product;
import com.dradest.backend.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(ProductTestConfiguration.class)
@WebMvcTest(ProductRestController.class)
public class ProductRestControllerTest {

    private static final List<Product> productList = new ArrayList<>();

    static {
        Product productOne = new Product();
        productOne.setName("productOne");
        productOne.setDescription("productOne description");
        productOne.setPrice(BigDecimal.ONE);
        productList.add(productOne);
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getProductsShouldReturnListOfProducts() throws Exception {
        when(productService.fetchAllProducts()).thenReturn(productList);
        String mvcResult = this.mockMvc.perform(get("/products"))
                //.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/hal+json"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertNotNull(mvcResult);
        String expectedResult = "{\"_embedded\":{\"productList\":[{\"id\":null,\"name\":\"productOne\",\"description\":\"productOne description\",\"price\":1,\"_links\":{\"self\":{\"href\":\"http://localhost/products/productOne\"},\"products\":{\"href\":\"http://localhost/products\"}}}]},\"_links\":{\"self\":{\"href\":\"http://localhost/products\"}}}";
        assertEquals(expectedResult, mvcResult);
    }

    @Test
    public void getProductsWithNameShouldReturnOneProduct() throws Exception {
        when(productService.fetchProductByName("productOne")).thenReturn(productList.get(0));
        this.mockMvc.perform(get("/products/productOne"))
                //.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/hal+json"),
                        jsonPath("$.name", is("productOne"))
                );
    }

    @Test
    public void postNewProductShouldReturnNewProduct() throws Exception {
        Product postProduct = new Product();
        postProduct.setName("postProduct");
        postProduct.setDescription("post product description");
        postProduct.setPrice(BigDecimal.ONE);
        when(productService.persistProduct(postProduct)).thenReturn(postProduct);
        this.mockMvc.perform(
                post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writeValueAsString(postProduct))
                )
                //.andDo(print())
                .andExpectAll(
                        status().is2xxSuccessful() // returns status 201: resource successfully created
                );
    }

    private String writeValueAsString(Product product) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(product);
    }

    @Test
    public void putProductShouldUpdateProduct() throws Exception {
        Product putProduct = new Product();
        putProduct.setName("putProduct");
        putProduct.setDescription("put product description");
        putProduct.setPrice(BigDecimal.ONE);
        when(productService.updateProduct(putProduct)).thenReturn(putProduct);
        this.mockMvc.perform(
                put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writeValueAsString(putProduct))
                )
                //.andDo(print())
                .andExpectAll(
                        status().is2xxSuccessful()
                );
    }

    @Test
    public void deleteProductShouldDeleteProduct() throws Exception {
        this.mockMvc.perform(delete("/products/deleteProduct"))
                //.andDo(print())
                .andExpectAll(
                        status().is2xxSuccessful() // returns 204: no content success status
                );
    }
}
