package com.produc.infraestructure.presistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.produc.infraestructure.presistence.entities.ProductEntity;

@Repository
public interface PorductPeristence extends JpaRepository<ProductEntity, Long> {

}
