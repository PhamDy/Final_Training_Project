import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { SliderComponent } from './layout/slider/slider.component';
import { FooterComponent } from './layout/footer/footer.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CategoryModule } from './pages/categories/category.module';
import { HTTP_INTERCEPTORS, HttpClientModule, HttpClientXsrfModule } from '@angular/common/http';
import { CommonModule } from '@angular/common'; 
import { ProductModule } from './pages/products/product.module';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { AuthInterceptor } from './keycloak/AuthInterceptor';

function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:9091',
        realm: 'master',
        clientId: 'microservice_ecommerce'
      },
      initOptions: {
        pkceMethod: 'S256',
        redirectUri: 'http://localhost:4200',
      }, loadUserProfileAtStartUp: false
    });
}

@NgModule({
  declarations: [	
    AppComponent,
    SliderComponent,
    NavbarComponent,
    DashboardComponent,
    FooterComponent
   ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CategoryModule,
    ProductModule,
    HttpClientModule,
    CommonModule,
    KeycloakAngularModule,
    HttpClientXsrfModule.withOptions({
      cookieName: 'XSRF-TOKEN',
      headerName: 'X-XSRF-TOKEN',
    }),
  ],
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: initializeKeycloak,
      multi: true,
      deps: [KeycloakService]
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
