package com.dradest.backend.product.controller;

import com.dradest.backend.product.exceptions.ProductDataIncompleteException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductDataIncompleteAdvice {
    @ExceptionHandler(ProductDataIncompleteException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    String productNPEHandler(ProductDataIncompleteException ex) {
        return ex.getMessage();
    }
}
