package com.produc.infraestructure.controllers.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.produc.domain.models.Product;
import com.produc.infraestructure.controllers.models.ProductDto;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

	ProductDto toProductDto(Product product);

	Product toProduct(ProductDto productDto);

	List<ProductDto> toProduct(List<Product> products);

}
