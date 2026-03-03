package com.produc.infraestructure.presistence.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.produc.domain.models.Product;
import com.produc.infraestructure.presistence.entities.ProductEntity;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	Product toProductEntity(ProductEntity productEntity);

	ProductEntity toProduct(Product product);

	List<Product> toProducts(List<ProductEntity> productEntities);
}
