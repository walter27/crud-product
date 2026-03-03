package com.produc.application;

import java.util.List;

import org.springframework.stereotype.Component;

import com.produc.application.repositories.ProductRepository;
import com.produc.application.services.ProductService;
import com.produc.domain.exceptions.ProductException;
import com.produc.domain.models.Product;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductAdapter implements ProductService {

    private final ProductRepository repository;

    @Override
    public Product create(Product product) {
        product.setId(null);
        return repository.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        Product productFound = repository.findById(id).orElseThrow(() -> new ProductException(id));
        productFound.setName(product.getName());
        productFound.setPrice(product.getPrice());
        productFound.setStock(product.getStock());
        return repository.save(productFound);
    }

    @Override
    public Product findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductException(id));
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductException(id));
        repository.deleteById(product.getId());
    }
}
