import { Routes } from '@angular/router';

export const routes: Routes = [

    {
        pathMatch: 'full',
        path: '',
        redirectTo: 'products',
    },
    {
        path: 'products',
        loadComponent: () => import('./product/product').then(m => m.Product)
    }
];
