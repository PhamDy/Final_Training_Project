import { Component, OnInit } from '@angular/core';
import { CategoryServiceService } from '../service/categoryService.service';
import { Category } from '../model/category';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit{

  constructor(private categoryService: CategoryServiceService, private router:Router){}
  category: Category[] = [];
  ngOnInit(): void {
      this.getAll();
  }

  getAll(){
    this.categoryService.getAll().subscribe((res) => {
      this.category = res;
    })
  }

  onEditCategory(item: Category){
    this.router.navigateByUrl('category/' + item.category_id);
  }

  onDelete(item: Category){
    alert("sd");
  }
  
}
