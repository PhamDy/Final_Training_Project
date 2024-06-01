import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductResponse } from 'src/app/models/product-response';
import { environment } from 'src/environments/environment.development';
import { PageProduct } from 'src/app/models/page-product';
import { Page } from 'src/app/models/page';
import { Product } from 'src/app/models/product';


const urlPublic = environment.urlPublic;
const urlPrivate = environment.urlPrivate;
const endPoint = 'product/';

@Injectable({
  providedIn: 'root'
})

export class ProductService {

  constructor(private http:HttpClient) { }

    // Make call to the back end API to retrieve page of users
    product$ = (name: string = ''): Observable<PageProduct> => 
      this.http.get<PageProduct>(`${urlPublic + endPoint}?name=${name}`)

  getProducts(name: string = '', size: number = 10, offset:number): Observable<Page<ProductResponse>>{
    return this.http.get<Page<ProductResponse>>(`${urlPublic + endPoint}?name=${name}&size=${size}&offset=${offset}`)
  }

  getProductById(id: string): Observable<Product>{
    return this.http.get<Product>(`${urlPublic + endPoint + id}`)
  }

  addProduct(product: FormData): Observable<void>{
    return this.http.post<void>(`${urlPrivate + endPoint + 'saveProduct'}`,
      product
    );
  }

  updateProduct(id: string ,product: FormData): Observable<void>{
    return this.http.put<void>(`${urlPrivate + endPoint + 'editProduct/' +id}`,
      product
    );
  }

}
