import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ProductDto } from '../models/productDto';
import { environment } from '../../../environments/environment.development';
import { productAdapter, productDtoAdapter, productsAdapter } from '../mappers/productMapper';
import { map } from 'rxjs';
import { Store } from '../store/store';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root',
})
export class HtppService {
  private baseUrl: string = environment.baseUrl;
  private storeService = inject(Store);
  private http = inject(HttpClient);

  getProducts(): void {
    const newProductsMap = new Map<number, Product>();
    this.http
      .get<ProductDto[]>(`${this.baseUrl}/products`)
      .pipe(map((response) => productsAdapter(response)))
      .subscribe((products) => {
        products.forEach((product: Product) => {
          newProductsMap.set(product.id, product);
        });
        this.storeService.setProducts(newProductsMap);
      });
  }

  addProduct(product: Product): void {
    const productDto = productDtoAdapter(product);
    const products = new Map<number, Product>(this.storeService.products() ?? []);
    this.http.post<ProductDto>(`${this.baseUrl}/products`, productDto).subscribe((response) => {
      const createdProduct = productAdapter(response);
      products.set(createdProduct.id, createdProduct);
      this.storeService.setProducts(products);
    });
  }

  updateProduct(product: Product): void {
    const productDto = productDtoAdapter(product);
    const products = new Map<number, Product>(this.storeService.products() ?? []);

    this.http.put<ProductDto>(`${this.baseUrl}/products`, productDto).subscribe((response) => {
      const updatedProduct = productAdapter(response);
      products.set(updatedProduct.id, updatedProduct);
      this.storeService.setProducts(products);
    });
  }

  deleteProduct(id: number): void {
    const products = new Map<number, Product>(this.storeService.products() ?? []);

    this.http.delete(`${this.baseUrl}/products/${id}`).subscribe(() => {
      products.delete(id);
      this.storeService.setProducts(products);
    });
  }
}

