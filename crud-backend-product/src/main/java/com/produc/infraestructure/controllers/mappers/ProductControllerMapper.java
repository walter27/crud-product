package com.produc.infraestructure.controllers.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.produc.domain.models.Product;
import com.produc.infraestructure.models.ProductDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductControllerMapper {

    ProductDto toProduct(Product product);

    Product toProductDto(ProductDto productDto);

    List<ProductDto> toProductList(List<Product> products);
}
