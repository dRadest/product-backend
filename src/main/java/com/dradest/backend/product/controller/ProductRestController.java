package com.dradest.backend.product.controller;

import com.dradest.backend.product.jpa.model.Product;
import com.dradest.backend.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    protected List<Product> all() {
        return productService.fetchAllProducts();
    }

    @GetMapping("/products/{name}")
    protected Product one(@PathVariable String name) {
        return productService.fetchProductByName(name);
    }

    @PostMapping("/products")
    protected Product newProduct(@RequestBody Product newProduct) {
        return productService.persistProduct(newProduct);
    }

    @PutMapping("/products")
    protected Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/products/{name}")
    protected void deleteProduct(@PathVariable String name) {
        productService.deleteProduct(name);
    }
}
