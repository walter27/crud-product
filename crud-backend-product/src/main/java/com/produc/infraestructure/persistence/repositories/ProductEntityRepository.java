package com.produc.infraestructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.produc.infraestructure.persistence.entities.ProductEntity;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {
}