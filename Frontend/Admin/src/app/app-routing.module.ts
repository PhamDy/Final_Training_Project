import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ProductListComponent } from './pages/products/product-list/product-list.component';
import { ProductFormComponent } from './pages/products/product-form/product-form.component';
import { CategoryListComponent } from './pages/categories/category-list/category-list.component';
import { CategoryFormComponent } from './pages/categories/category-form/category-form.component';
import { UserComponent } from './pages/user/user.component';

const routes: Routes = [
  {
    path: "",
    component: DashboardComponent
  },
  {
    path: "product",
    component: ProductListComponent
  },
  {
    path: "product/:id",
    component: ProductFormComponent
  },
  {
    path: "category",
    component: CategoryListComponent
  },
  {
    path: "category/:id",
    component: CategoryFormComponent
  },
  {
    path: "newCategory",
    component: CategoryFormComponent
  },
  {
    path: "user",
    component: UserComponent
  },
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
