import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiResponse } from 'src/app/models/ApiResponse';
import { Category } from 'src/app/models/category';
import { environment } from 'src/environments/environment.development';

const urlPublic = environment.urlPublic;
const urlPrivate = environment.urlPrivate;
const endPoint = 'product/';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private http: HttpClient) {}

  getCategory(): Observable<Category[]> {
    return this.http.get<Category[]>(urlPublic + endPoint + 'category');
  }

  getCategoryById(Id: string): Observable<Category> {
    return this.http.get<Category>(
      `${urlPublic + endPoint + 'category' + '/' + Id}`
    );
  }

  addCategory(category: Category): Observable<void> {
    return this.http.post<void>(
      `${urlPrivate + endPoint + 'saveCategory'}`,
      category
    );
  }

  updateCategory(id: string, category: Category): Observable<void> {
    return this.http.put<void>(
      `${urlPrivate + endPoint + 'category' + '/' + id}`,
      category
    );
  }

  deleteCategory(id: number): Observable<void> {
    return this.http.delete<void>(
      `${urlPrivate + endPoint + 'category' + '/' + id}`
    );
  }
}
