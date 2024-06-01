import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, Observable, catchError, map, of, startWith } from 'rxjs';
import { PageProduct } from 'src/app/models/page-product';
import { ProductService } from '../product.service';
import { ProductResponse } from 'src/app/models/product-response';
import { Page } from 'src/app/models/page';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  pageProduct: Page<ProductResponse> = new Page<ProductResponse>
  size: number = 10;
  offset: number = 0;
  totalPage: number[] = [];
  name: string;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts('', this.size, this.offset).subscribe(res => {
      this.pageProduct = res;
      this.totalPage = Array.from({ length: this.pageProduct.totalPage }, (_, i) => i );
    });
  }

  goToPage(offset: number) {
    this.offset = offset;
    this.productService.getProducts('', 10, offset).subscribe(res => {
      this.pageProduct = res;
    })
  }

  nextPage(): void {
    if (this.offset + this.size < this.pageProduct.totalElements) {
      this.offset += this.size;
      this.getProducts();
    }
  }

  previousPage(): void {
    if (this.offset - this.size >= 0) {
      this.offset -= this.size;
      this.getProducts();
    }
  }

 
}
