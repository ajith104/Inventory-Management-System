import { Routes } from '@angular/router';
import { Dashboard } from './components/dashboard/dashboard';
import { Products } from './components/products/products';
import { Suppliers } from './components/suppliers/suppliers';
import { Purchases } from './components/purchases/purchases';
import { Sales } from './components/sales/sales';
import { Users } from './components/users/users';

export const routes: Routes = [
    {path:'',redirectTo:'dashboard',pathMatch:'full'},
    {path:'dashboard',component:Dashboard},
    {path:'products',component:Products},
    {path:'suppliers',component:Suppliers},
    {path:'purchase',component:Purchases},
    {path:'sales',component:Sales},
    {path:'users',component:Users}

];
