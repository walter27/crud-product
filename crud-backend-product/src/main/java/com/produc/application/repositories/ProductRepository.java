package com.produc.application.repositories;

import java.util.List;
import java.util.Optional;

import com.produc.domain.models.Product;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);
}