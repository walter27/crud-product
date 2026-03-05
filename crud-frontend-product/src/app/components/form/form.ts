import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Store } from '../../product/store/store';
import { FieldConfig } from '../../product/models/product';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-form',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './form.html',
  styleUrl: './form.css',
})
export class Form {

  private storeService = inject(Store);
  public form!: FormGroup
  private fb = inject(FormBuilder)

  fields = this.storeService.fields
  fieldColumns = () => this.chunkFields(this.fields().filter((field) => !this.isHiddenField(field.name)), 3);

  ngOnInit(): void {
    const controls: any = {};
    this.fields().forEach(field => {
      controls[field.name] = [
        field.value ?? '',
        field.validators ?? []
      ];
    });
    this.form = this.fb.group(controls);

    const selectedProduct = this.storeService.productSelected();
    if (selectedProduct) {
      this.form.patchValue(selectedProduct);
    }
  }

  submit(): boolean {
    if (this.form.valid) {
      this.storeService.setObjectSelected(this.form.getRawValue(), 'product');
      return true;
    } else {
      this.form.markAllAsTouched();
      return false;
    }
  }

  hasError(name: string): boolean {
    const control = this.form.get(name);
    return !!(control && control.invalid && control.touched);
  }

  isHiddenField(name: string): boolean {
    return name === 'id';
  }

  private chunkFields(fields: FieldConfig[], size: number): FieldConfig[][] {
    const chunks: FieldConfig[][] = [];
    for (let index = 0; index < fields.length; index += size) {
      chunks.push(fields.slice(index, index + size));
    }
    return chunks;
  }
}
