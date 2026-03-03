package com.produc.infraestructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.produc.application.repositories.ProductRepository;
import com.produc.domain.models.Product;
import com.produc.infraestructure.persistence.mappers.ProductPersistenceMapper;
import com.produc.infraestructure.persistence.repositories.ProductEntityRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductRepository {

    private final ProductEntityRepository persistence;
    private final ProductPersistenceMapper mapper;

    @Override
    public Product save(Product product) {
        return mapper.toProduct(persistence.save(mapper.toProductEntity(product)));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return persistence.findById(id).map(mapper::toProduct);
    }

    @Override
    public List<Product> findAll() {
        return mapper.toProductList(persistence.findAll());
    }

    @Override
    public void deleteById(Long id) {
        persistence.deleteById(id);
    }
}
