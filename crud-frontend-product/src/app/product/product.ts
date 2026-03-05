import { Component } from '@angular/core';
import { Manage } from './components/manage/manage';

@Component({
  selector: 'app-product',
  imports: [Manage],
  templateUrl: './product.html',
  styleUrl: './product.css',
})
export class Product {}
