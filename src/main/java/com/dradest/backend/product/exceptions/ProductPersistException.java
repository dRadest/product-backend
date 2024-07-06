package com.dradest.backend.product.exceptions;

public class ProductPersistException extends RuntimeException {
    public ProductPersistException(String name) {
        super("Could not persist new product. Product with name " + name + " already exists");
    }
}
