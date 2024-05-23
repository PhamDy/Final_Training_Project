import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductComponent } from './product/product.component';
import { UserComponent } from './user/user.component';
import { ContentComponent } from './content/content.component';
import { CategoryComponent } from './category/category.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { CategorydetailsComponent } from './categorydetails/categorydetails.component';

const routes: Routes = [
  {
    path: '',
    component: ContentComponent
  },
  {
    path: 'content',
    component: ContentComponent
  },
  {
    path: 'product',
    component: ProductComponent
  },
  {
    path: 'product/:id',
    component: ProductDetailsComponent
  },
  {
    path: 'category',
    component: CategoryComponent
  },
  {
    path: 'category/:id',
    component: CategorydetailsComponent
  },
  {
    path: 'user',
    component: UserComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
