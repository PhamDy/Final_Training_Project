import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CategoryServiceService } from '../service/categoryService.service';
import { FormBuilder, FormGroup, Validators  } from '@angular/forms';
import { Category } from '../model/category';
import { Router } from '@angular/router';

@Component({
  selector: 'app-categorydetails',
  templateUrl: './categorydetails.component.html',
  styleUrls: ['./categorydetails.component.css']
})
export class CategorydetailsComponent implements OnInit {

  constructor(
    private activedRoute: ActivatedRoute,
    private categoryService: CategoryServiceService,
    private router:Router,
    private fb: FormBuilder
  ) {}

  form!: FormGroup
  category = new Category();
  ngOnInit() {
    this.InitForm();
    let itemId = this.activedRoute.snapshot.params['id'];
    this.getByCategoryId(itemId);
  }

  getByCategoryId(Id: number){
    this.categoryService.getById(Id).subscribe((res) => {
      this.category = res;
      console.log(this.category);
      this.bindValueForm();
    })
  }

  InitForm() {
    this.form = this.fb.group({
      id: [0],
      name: [null, Validators.required]
    });
  }

  bindValueForm() {
    this.form.patchValue({
      id: this.category.category_id,
      name: this.category.name
    });
  }

  onSave() {
    if (this.form.valid) {
      let valueForm = this.form.getRawValue();

      this.categoryService.editById(this.category.category_id!, valueForm.name).subscribe(res => {
        console.log('Category updated:', res);
        // this.router.navigate([`/category/${this.category.category_id}`]);
        this.router.navigate(['/category']); // Đ
      });
    }
  }

}
