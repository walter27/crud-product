import { Component, computed, inject, ViewChild } from '@angular/core';
import { Validators } from '@angular/forms';
import { FieldConfig, Product } from '../../models/product';
import { Table } from '../../../components/table/table';
import { Modal } from '../../../components/modal/modal';
import { Form } from '../../../components/form/form';
import { Store } from '../../store/store';
import { CommonModule } from '@angular/common';
import { HtppService } from '../../services/htpp-service';
import { Success } from '../../../components/success/success';
import { Error } from '../../../components/error/error';

@Component({
  selector: 'app-manage',
  imports: [Table, Modal, Form, CommonModule, Success, Error],
  templateUrl: './manage.html',
  styleUrl: './manage.css',
})
export class Manage {

  @ViewChild(Form) formComponent?: Form;

  fields: FieldConfig[] = [
    {
      name: 'id',
      label: 'Id',
      type: 'text',
      validators: [],
    },
    {
      name: 'name',
      label: 'Name',
      type: 'text',
      validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
    },
    {
      name: 'price',
      label: 'Price',
      type: 'text',
      validators: [Validators.required, Validators.minLength(3), Validators.maxLength(50)],
    },
    {
      name: 'stock',
      label: 'Email',
      type: 'email',
      validators: [Validators.required],
    },
  ];

  storeService = inject(Store);
  httpService = inject(HtppService)


  readonly listaObjects = computed<Product[]>(() =>
    Array.from(this.storeService.products()?.values() ?? []),
  );

  openModal = this.storeService.openModal
  success = this.storeService.success
  error = this.storeService.error;


  onDelete(product: unknown): void {
    this.storeService.setObjectSelected(product as Product, 'product');
    this.httpService.deleteProduct()
  }

  onEdit(product: unknown): void {
    this.storeService.setObjectSelected(product as Product, 'product');
    this.httpService.updateProduct()
  }

  onModalSave(): void {
    if (this.error() || this.success()) {
      this.storeService.setOpenModal(false)
      return
    }
    this.formComponent?.submit();
    this.httpService.addProduct()
  }
}

