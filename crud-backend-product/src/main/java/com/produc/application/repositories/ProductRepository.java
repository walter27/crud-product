package com.produc.application.repositories;

import java.util.List;

import com.produc.domain.models.Product;

public interface ProductRepository {
	
	Product save(Product product);
	
	List<Product> findAll();
	
	void deleteById(Long id);
	
}
