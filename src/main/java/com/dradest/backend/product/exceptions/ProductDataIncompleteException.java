package com.dradest.backend.product.exceptions;

public class ProductDataIncompleteException extends RuntimeException {
    public ProductDataIncompleteException(String message) {
        super("Product data incomplete. " + message);
    }
}
