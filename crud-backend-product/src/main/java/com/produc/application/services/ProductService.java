package com.produc.application.services;

import java.util.List;

import com.produc.domain.models.Product;

public interface ProductService {

	Product save(Product product);

	List<Product> findAll();

	void deleteById(Long id);

	Product update(Product product);

}
