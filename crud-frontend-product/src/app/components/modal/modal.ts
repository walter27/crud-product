import { Component, output, inject } from '@angular/core';
import { Store } from '../../product/store/store';

@Component({
  selector: 'app-modal',
  imports: [],
  templateUrl: './modal.html',
  styleUrl: './modal.css',
})
export class Modal {


  saved = output<void>();
  private storeService = inject(Store);

  close(): void {
    this.storeService.setOpenModal(false)
  }

  save(): void {
    this.saved.emit();
  }
}
