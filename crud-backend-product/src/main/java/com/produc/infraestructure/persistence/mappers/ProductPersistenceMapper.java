package com.produc.infraestructure.persistence.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.produc.domain.models.Product;
import com.produc.infraestructure.persistence.entities.ProductEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductPersistenceMapper {

    Product toProduct(ProductEntity productEntity);

    ProductEntity toProductEntity(Product product);

    List<Product> toProductList(List<ProductEntity> productEntities);
}
