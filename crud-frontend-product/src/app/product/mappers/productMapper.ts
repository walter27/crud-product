import { Product } from "../models/product"
import { ProductDto } from "../models/productDto";

export const productsAdapter = (productDto: ProductDto[]): Product[] => {
    return productDto.map(product => {
        const { ...rest } = product;
        return {
            ...rest,
        };
    });
}

export const productDtoAdapter = (product: Product): ProductDto => {
    const { ...rest } = product;
    return {
        ...rest
    }
}

export const productAdapter = (product: ProductDto): Product => {
    const { ...rest } = product;
    return {
        ...rest
    }
}