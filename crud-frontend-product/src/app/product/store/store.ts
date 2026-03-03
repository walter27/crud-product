import { Injectable, computed, signal } from '@angular/core';
import { FieldConfig, Product } from '../models/product';
import { ProductDto } from '../models/productDto';

type StoreData = {
  openModal: boolean
  fields: FieldConfig[]
  loading: boolean
  success: boolean
  error: boolean
  product: Product | null
  products: Map<number, Product> | null
}

const initialStore: StoreData = {
  openModal: false,
  fields: [],
  loading: false,
  success: false,
  error: false,
  product: null,
  products: null

}
@Injectable({
  providedIn: 'root',
})
export class Store {

  private readonly state = signal<StoreData>(initialStore);

  readonly openModal = computed(() => this.state().openModal)
  readonly fields = computed(() => this.state().fields)
  readonly products = computed(() => this.state().products)

  setOpenModal(openModal: boolean) {
    this.state.update((s) => ({ ...s, openModal }))
  }

  setObject(form: any, type: string) {
    if (type === 'product') {
      this.state.update((s) => ({ ...s, product: { ...form } }))
    }
  }

  setProducts(products: Map<number, Product>) {
    this.state.update((s) => ({ ...s, products }))
  }


}
