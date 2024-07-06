package com.dradest.backend.product.controller;

import com.dradest.backend.product.exceptions.ProductPersistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductPersistAdvice {

    @ExceptionHandler(ProductPersistException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String productPersistHandler(ProductPersistException ex) {
        return ex.getMessage();
    }
}
