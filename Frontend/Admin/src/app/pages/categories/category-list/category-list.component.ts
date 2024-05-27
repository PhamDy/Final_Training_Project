import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../category.service';
import { Router } from '@angular/router';
import { Category } from 'src/app/models/category';

@Component({
  selector: 'app-category-list',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  categories: Category[] = [];

  constructor(private categoryService: CategoryService, private router: Router) { }

  ngOnInit(): void {
    this.categoryService.getCategory().subscribe(res => {
      this.categories = res;
    })
  }

  deleteCategory(id: number){
    this.categoryService.deleteCategory(id).subscribe(() => {  
      this.router.navigateByUrl('/category').then(() => {
        this.ngOnInit(); 
      });
    })
  }

}
