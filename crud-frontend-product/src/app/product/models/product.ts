import { ValidatorFn } from '@angular/forms';

export type FieldType =
    | 'text'
    | 'email'
    | 'password'
    | 'select'
    | 'radio'
    | 'checkbox';

export interface FieldOption {
    label: string;
    value: any;
}

export interface FieldConfig {
    name: string;
    label: string;
    type: FieldType;
    placeholder?: string;
    value?: any;
    options?: FieldOption[];
    validators?: ValidatorFn[];
}

export interface Product {
    id: number;
    name: string;
    price: string;
    stock: number
}