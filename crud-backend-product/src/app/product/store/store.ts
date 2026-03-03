import { Injectable, computed, signal } from '@angular/core';
import { FieldConfig } from '../models/product';
import { ProductDto } from '../models/productDto';

type StoreData = {
  openModal: boolean
  fields: FieldConfig[]
  loading: boolean
  success: boolean
  error: boolean
  productDto: ProductDto | null
}

const initialStore: StoreData = {
  openModal: false,
  fields: [],
  loading: false,
  success: false,
  error: false,
  productDto: null

}
@Injectable({
  providedIn: 'root',
})
export class Store {

  private readonly state = signal<StoreData>(initialStore);

  readonly openModal = computed(() => this.state().openModal)
  readonly fields = computed(() => this.state().fields)

  setOpenModal(openModal: boolean) {
    this.state.update((s) => ({ ...s, openModal }))
  }

  setObject(form: any, type: string) {
    if (type === 'product') {
      this.state.update((s) => ({ ...s, productDto: { ...form } }))
    }

  }
}
