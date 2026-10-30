import { Routes } from '@angular/router';
import { Dashboard } from './components/dashboard/dashboard';
import { Products } from './components/products/products';
import { Suppliers } from './components/suppliers/suppliers';
import { Purchases } from './components/purchases/purchases';
import { Sales } from './components/sales/sales';
import { Users } from './components/users/users';
import { Login } from './components/login/login';
import { authGuard } from './services/auth.guard';

export const routes: Routes = [
    { path: '',redirectTo: 'login', pathMatch:'full' },
    { path: 'dashboard', component:Dashboard, canActivate: [authGuard]},
    { path: 'products', component:Products, canActivate: [authGuard] },
    { path: 'suppliers', component:Suppliers, canActivate: [authGuard], data: { roles: ['OWNER', 'MANAGER'] } },
    { path: 'purchase', component:Purchases, canActivate: [authGuard], data: { roles: ['OWNER', 'MANAGER'] } },
    { path: 'sales', component:Sales, canActivate: [authGuard] },
    { path: 'users', component:Users, canActivate: [authGuard], data: { roles: ['OWNER', 'MANAGER'] } },
    { path: 'login', component:Login },
    { path: '**', redirectTo:'login'}

];
