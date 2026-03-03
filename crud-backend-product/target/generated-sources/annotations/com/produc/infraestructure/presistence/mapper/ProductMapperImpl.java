package com.produc.infraestructure.presistence.mapper;

import com.produc.domain.models.Product;
import com.produc.infraestructure.presistence.entities.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-03T12:13:46-0500",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 25.0.1 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toProductEntity(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        Product product = new Product();

        product.setId( productEntity.getId() );
        product.setName( productEntity.getName() );
        product.setPrice( productEntity.getPrice() );
        product.setStock( productEntity.getStock() );

        return product;
    }

    @Override
    public ProductEntity toProduct(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId( product.getId() );
        productEntity.setName( product.getName() );
        productEntity.setPrice( product.getPrice() );
        productEntity.setStock( product.getStock() );

        return productEntity;
    }

    @Override
    public List<Product> toProducts(List<ProductEntity> productEntities) {
        if ( productEntities == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( productEntities.size() );
        for ( ProductEntity productEntity : productEntities ) {
            list.add( toProductEntity( productEntity ) );
        }

        return list;
    }
}
