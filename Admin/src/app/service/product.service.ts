import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductResponse } from '../model/product-response';
import { environment } from '../environments/environment.development';

const url = environment.baseUrlPublic;

@Injectable({
  providedIn: 'root',
})
export class ProductService {

  constructor(private http: HttpClient) {}

  getAll(): Observable<ProductResponse[]> {
    return this.http.get<ProductResponse[]>(url + 'product/');
  }
  
}
