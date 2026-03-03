package com.produc.domain.exceptions;

public class ProductException extends RuntimeException {

    public ProductException(Long id) {
        super("Product not found with id: " + id);
    }
}