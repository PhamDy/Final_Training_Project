import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../model/category';
import { environment } from '../environments/environment.development';

const url = environment.baseUrlPublic;

@Injectable({
  providedIn: 'root'
})
export class CategoryServiceService {

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Category[]>{
    return this.http.get<Category[]>(url + 'product/category', {});
  }

  getById(Id: number): Observable<Category>{
    return this.http.get<Category>(`${url + 'product/category/' + Id}`, {});
  }

  editById(Id: number, name: string): Observable<Category> {
    return this.http.put<Category>(`${url + 'product/category/' + Id}`, { name });
  }

}
