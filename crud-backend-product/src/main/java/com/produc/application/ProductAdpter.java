package com.produc.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.produc.application.repositories.ProductRepository;
import com.produc.application.services.ProductService;
import com.produc.domain.models.Product;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ProductAdpter implements ProductService {

	private final ProductRepository repository;

	@Override
	public Product save(Product product) {
		return repository.save(product);
	}

	@Override
	public List<Product> findAll() {
		return repository.findAll();
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Product update(Product product) {
		return repository.save(product);
	}

}
