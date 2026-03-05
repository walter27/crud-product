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
  modalMode: 'form' | 'delete' = 'form';

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
      label: 'Stock',
      type: 'text',
      validators: [Validators.required],
    },
  ];

  storeService = inject(Store);
  httpService = inject(HtppService)

  readonly listaObjects = computed(() => Array.from(this.storeService.products()?.values() ?? []));

  ngOnInit(): void {
    this.httpService.getProducts();
    this.storeService.setFields(this.fields)
  }

  openModal = this.storeService.openModal
  success = this.storeService.success
  error = this.storeService.error;

  onAdd(): void {
    this.modalMode = 'form';
    this.storeService.setLoadingAndSuccesAndError(false, false);
    this.storeService.setObjectSelected(null, 'product');
    this.storeService.setOpenModal(true);
  }


  onDelete(product: unknown): void {
    this.modalMode = 'delete';
    this.storeService.setLoadingAndSuccesAndError(false, false);
    this.storeService.setObjectSelected(product as Product, 'product');
    this.storeService.setOpenModal(true)
  }

  onEdit(product: unknown): void {
    this.modalMode = 'form';
    this.storeService.setLoadingAndSuccesAndError(false, false);
    this.storeService.setObjectSelected(product as Product, 'product');
    this.storeService.setOpenModal(true)
  }

  onModalSave(): void {
    if (this.error() || this.success()) {
      this.storeService.setOpenModal(false)
      this.storeService.setLoadingAndSuccesAndError(false, false);
      this.modalMode = 'form';
      return
    }

    if (this.modalMode === 'delete') {
      this.httpService.deleteProduct()
      return
    }

    const isValid = this.formComponent?.submit() ?? false;
    if (!isValid) {
      return;
    }

    const productSelected = this.storeService.productSelected();
    const idValue = productSelected?.id;
    const hasValidId = idValue !== null && idValue !== undefined && String(idValue).trim() !== '' && Number(idValue) > 0;
    if (hasValidId) {
      this.httpService.updateProduct()
      return
    }

    this.httpService.addProduct()
  }
}
