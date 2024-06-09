import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ProductListComponent } from './pages/products/product-list/product-list.component';
import { ProductFormComponent } from './pages/products/product-form/product-form.component';
import { CategoryListComponent } from './pages/categories/category-list/category-list.component';
import { CategoryFormComponent } from './pages/categories/category-form/category-form.component';
import { UserComponent } from './pages/user/user.component';
import { AuthKeyClockGuard } from './routeguards/auth.route';

const routes: Routes = [
  {
    path: "",
    component: DashboardComponent,
    canActivate: [AuthKeyClockGuard]
  },
  {
    path: "product",
    component: ProductListComponent,
    canActivate: [AuthKeyClockGuard], data: {
      roles: ['User' ,'Admin']
    }
  },
  {
    path: "product/:id",
    component: ProductFormComponent,
    canActivate: [AuthKeyClockGuard], data: {
      roles: ['Admin']
    }
  },
  {
    path: "newProduct",
    component: ProductFormComponent,
    canActivate: [AuthKeyClockGuard], data: {
      roles: ['Admin']
    }
  },
  {
    path: "category",
    component: CategoryListComponent,
    canActivate: [AuthKeyClockGuard], data: {
      roles: ['User' ,'Admin']
    }
  },
  {
    path: "category/:id",
    component: CategoryFormComponent,
    canActivate: [AuthKeyClockGuard], data: {
      roles: ['User' ,'Admin']
    }
  },
  {
    path: "newCategory",
    component: CategoryFormComponent,
    canActivate: [AuthKeyClockGuard], data: {
      roles: ['User' ,'Admin']
    }
  },
  {
    path: "user",
    component: UserComponent,
    canActivate: [AuthKeyClockGuard], data: {
      roles: ['Admin']
    }
  },
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
