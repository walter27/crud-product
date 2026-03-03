package com.produc.application.services;

import java.util.List;

import com.produc.domain.models.Product;

public interface ProductService {

    Product create(Product product);

    Product update(Long id, Product product);

    Product findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);
}