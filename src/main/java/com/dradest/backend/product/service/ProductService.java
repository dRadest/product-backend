package com.dradest.backend.product.service;

import com.dradest.backend.product.exceptions.ProductDataIncompleteException;
import com.dradest.backend.product.exceptions.ProductNotFoundException;
import com.dradest.backend.product.exceptions.ProductPersistException;
import com.dradest.backend.product.jpa.model.Product;
import com.dradest.backend.product.jpa.repositories.ProductJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductJpaRepository productJpaRepository;

    public ProductService(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    public List<Product> fetchAllProducts() {
        return productJpaRepository.findAll();
    }

    public Product fetchProductByName(String name) {
        if (name == null) {
            throw new ProductDataIncompleteException("product name NULL");
        }
        Product result;
        Optional<Product> optProduct = productJpaRepository.findByName(name);
        if (optProduct.isPresent()) {
            result = optProduct.get();
        } else {
            throw new ProductNotFoundException(name);
        }

        return result;
    }

    public Product updateProduct(Product product) {
        if (product == null) {
            throw new ProductDataIncompleteException("product NULL");
        }
        String productName = product.getName();
        if (productName == null) {
            throw new ProductDataIncompleteException("product name NULL");
        }

        Product result;

        Optional<Product> optProduct = productJpaRepository.findByName(productName);
        if (optProduct.isPresent()) {
            Product dbProduct = optProduct.get();
            dbProduct.setDescription(product.getDescription());
            dbProduct.setPrice(product.getPrice());
            result = productJpaRepository.save(dbProduct);
        } else {
            result = productJpaRepository.save(product);
        }

        return result;
    }

    public Product persistProduct(Product product) {
        if (product == null) {
            throw new ProductDataIncompleteException("product NULL");
        }
        String productName = product.getName();
        if (productName == null) {
            throw new ProductDataIncompleteException("product name NULL");
        }

        Product result;
        Optional<Product> optProduct = productJpaRepository.findByName(productName);
        if (optProduct.isPresent()) {
            throw new ProductPersistException(productName);
        } else {
            result = productJpaRepository.save(product);
        }

        return result;
    }

    public void deleteProduct(String name) {
        productJpaRepository.deleteByName(name);
    }
}
