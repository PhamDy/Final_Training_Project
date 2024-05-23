import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProductResponse } from '../model/product-response';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private url = 'http://localhost:9091/public/api/v1/product/';

  constructor(private httpClient: HttpClient) {}

  getAll(): Observable<ProductResponse[]> {
    return this.httpClient.get<ProductResponse[]>(this.url);
  }
  
}
