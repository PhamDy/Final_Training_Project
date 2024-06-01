import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { RouterModule } from '@angular/router';

import { AppComponent } from './app.component';
import { PreloaderComponent } from './preloader/preloader.component';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { SliderComponent } from './layout/slider/slider.component';
import { FooterComponent } from './layout/footer/footer.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CategoryModule } from './pages/categories/category.module';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common'; 
import { ProductModule } from './pages/products/product.module';



@NgModule({
  declarations: [	
    AppComponent,
    SliderComponent,
    NavbarComponent,
    PreloaderComponent,
    DashboardComponent,
    FooterComponent
   ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CategoryModule,
    ProductModule,
    HttpClientModule,
    CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
