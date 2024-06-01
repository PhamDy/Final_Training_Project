import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../category.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { window } from 'rxjs';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrls: ['./category-form.component.css'],
})
export class CategoryFormComponent implements OnInit {
  categoryForm: FormGroup = new FormGroup({});

  constructor(
    private formBuilder: FormBuilder,
    private categoryService: CategoryService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.categoryForm = this.formBuilder.group({
      name: ['', Validators.required],
      created_by: ['', Validators.required],
      created_at: [''],
      updated_by: ['', Validators.required],
      updated_at: [''],
    });

    let id = this.activatedRoute.snapshot.paramMap.get('id');

    if (id) {
      this.categoryService.getCategoryById(id).subscribe((category) => {
        if (category) {
          console.log(category);
          this.categoryForm.patchValue(category);
        } else {
          console.log('Category not found');
        }
      });
    }
  }

  onSubmit() {
    if (this.categoryForm.valid) {
      let category: Category = this.categoryForm.value;

      let id = this.activatedRoute.snapshot.paramMap.get('id');

      console.log(category);

      if (id) {
        // Update
        this.categoryService.updateCategory(id, category).subscribe(() => {
          console.log('Update successfully!');
          console.log(category);
        this.router.navigateByUrl('/category');
        });
      } else {
        // Add
        this.categoryService.addCategory(category).subscribe(() => {
          console.log('Add successfully!');
          console.log(category);
         this.router.navigateByUrl('/category');
        });
      }
      // this.router.navigateByUrl('/category').then(() => {
      //   this.reloadComponent();
      // });
    }
  }

  reloadComponent() {
    this.router.navigateByUrl('/category').then(() => {
      this.router.navigate([this.router.url]);
    });
  }
}
