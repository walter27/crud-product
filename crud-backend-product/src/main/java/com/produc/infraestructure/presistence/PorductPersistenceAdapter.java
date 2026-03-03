package com.produc.infraestructure.presistence;

import java.util.List;

import org.springframework.stereotype.Component;

import com.produc.application.repositories.ProductRepository;
import com.produc.domain.models.Product;
import com.produc.infraestructure.presistence.mapper.ProductMapper;
import com.produc.infraestructure.presistence.repository.PorductPeristence;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PorductPersistenceAdapter implements ProductRepository {

	private final PorductPeristence persitence;
	private final ProductMapper mapper;

	@Override
	public Product save(Product product) {
		return mapper.toProductEntity(persitence.save(mapper.toProduct(product)));
	}

	@Override
	public List<Product> findAll() {
		return mapper.toProducts(persitence.findAll());
	}

	@Override
	public void deleteById(Long id) {
		persitence.deleteById(id);

	}

}
