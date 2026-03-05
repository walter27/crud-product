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
      }, (err) => {
        this.storeService.setLoadingAndSuccesAndError(false, true)
      });
  }

  addProduct(): void {
    const productSelected = this.storeService.productSelected()
    const productDto = productDtoAdapter(productSelected!);
    const products = new Map<number, Product>(this.storeService.products() ?? []);
    this.http.post<ProductDto>(`${this.baseUrl}/products`, productDto).subscribe((response) => {
      const createdProduct = productAdapter(response);
      products.set(createdProduct.id, createdProduct);
      this.storeService.setProducts(products);
      this.storeService.setLoadingAndSuccesAndError(true, false);
    }, (err) => {
      this.storeService.setLoadingAndSuccesAndError(false, true)
    });
  }

  updateProduct(): void {
    const productSelected = this.storeService.productSelected()
    const productId = Number(productSelected?.id);
    if (!Number.isFinite(productId)) {
      this.storeService.setLoadingAndSuccesAndError(false, true);
      return;
    }

    const productDto = productDtoAdapter(productSelected!);
    const products = new Map<number, Product>(this.storeService.products() ?? []);
    this.http.put<ProductDto>(`${this.baseUrl}/products/${productId}`, productDto).subscribe((response) => {
      const updatedProduct = productAdapter(response);
      products.set(updatedProduct.id, updatedProduct);
      this.storeService.setProducts(products);
      this.storeService.setLoadingAndSuccesAndError(true, false);
    }, (err) => {
      this.storeService.setLoadingAndSuccesAndError(false, true)
    });
  }

  deleteProduct(): void {
    const products = new Map<number, Product>(this.storeService.products() ?? []);
    const productSelected = this.storeService.productSelected()
    this.http.delete(`${this.baseUrl}/products/${productSelected!.id}`).subscribe(() => {
      products.delete(productSelected!.id);
      this.storeService.setProducts(products);
      this.storeService.setLoadingAndSuccesAndError(true, false);
    }, (err) => {
      this.storeService.setLoadingAndSuccesAndError(false, true)
    });
  }
}
