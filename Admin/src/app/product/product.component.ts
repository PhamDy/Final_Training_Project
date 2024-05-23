import { Component, OnInit } from '@angular/core';
import { ProductService } from '../service/product.service';
import { ProductResponse } from '../model/product-response';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit{
  constructor(private productService: ProductService){

  }

  productResponse: ProductResponse[] = [];

  ngOnInit(): void {
    this.getAll();
  }

  getAll(){
    this.productService.getAll().subscribe((res) => {
      this.productResponse = res;
    })
  }

}
