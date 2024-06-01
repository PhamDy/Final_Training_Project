import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { CategoryService } from '../../categories/category.service';
import { Category } from 'src/app/models/category';
import { Observable } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from 'src/app/models/product';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ProductInput } from 'src/app/models/productInput';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css'],
})
export class ProductFormComponent implements OnInit {

  proudctInput = new ProductInput();
  productForm: FormGroup = new FormGroup({});
  categories: Category[] = [];
  avatar: File | null = null; 
  img1: File | null = null; 
  img2: File | null = null; 
  img3: File | null = null; 
  avatarPreview: SafeUrl | null = null;
  img1Preview: SafeUrl | null = null;
  img2Preview: SafeUrl | null = null;
  img3Preview: SafeUrl | null = null;

  avatarById: string;
  img1ById: string;
  img2ById: string;
  img3ById: string;
  isLoading: boolean = false;

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private formBuider: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    this.productForm = this.formBuider.group({
      product_name: ['', Validators.required],
      category_id: ['', Validators.required],
      avatar: ['', Validators.required],
      img1: ['', Validators.required], 
      description: ['', Validators.required],
      price: [null, [Validators.required, Validators.min(0)]],
      discount: [
        null,
        [Validators.required, Validators.min(0), Validators.max(100)],
      ],
      quantity: [null, [Validators.required, Validators.min(0)]],
      created_by: ['', Validators.required],
      updated_by: ['', Validators.required],
    });
    this.getCategory();
    
    let id = this.activatedRoute.snapshot.paramMap.get('id');
    if (id) {
      this.productService.getProductById(id).subscribe((product) => {
        if (product) {
          this.mapToInput(product);
          this.productForm.patchValue(this.proudctInput);
          this.avatarById = product.avatar;
          this.img1ById = product.img1;
          this.img2ById = product.img2;
          this.img3ById = product.img3;
          
          console.log(product);
        } else {
          console.log('Product not found');
        }
      });
    }
  }

  getCategory(): void {
    this.categoryService.getCategory().subscribe((res) => {
      this.categories = res;
      console.log(res);
    });
  }

  prepareFormData(product: ProductInput): FormData {
    const formData = new FormData();
    formData.append('avatar', this.avatar); 
    formData.append('img1', this.img1); 
    formData.append('img2', this.img2); 
    formData.append('img3', this.img3); 
    formData.append('category_id', product.category_id.toString());
    formData.append('product_name', product.product_name);
    formData.append('price', product.price.toString());
    formData.append('description', product.description);
    formData.append('discount', product.discount.toString());
    formData.append('quantity', product.quantity.toString());
    formData.append('created_by', product.created_by.toString());
    formData.append('updated_by', product.updated_by.toString());
    return formData;
  }

  onSubmit() {
    if (this.productForm.valid) {
      this.isLoading = true;
      let product: ProductInput = this.productForm.getRawValue();
      console.log(product);

      let id = this.activatedRoute.snapshot.paramMap.get('id');

      if (id) {
        // Update
        this.productService.updateProduct(id, this.prepareFormData(product)).subscribe(() => {
          console.log('Update successfully!');
          console.log(this.prepareFormData(product));
          this.router.navigateByUrl('/product').then(() => {
            this.isLoading = false;
            this.reloadComponent();
          });
        });
      } else {
        // Add
        this.productService.addProduct(this.prepareFormData(product)).subscribe(() => {
          console.log('Add successfully!');
          console.log(product);
          
          this.router.navigateByUrl('/product').then(() => {
            this.isLoading = false;
            this.reloadComponent();
          });
        });
      }      
    }
  }

  reloadComponent() {
    this.router
      .navigateByUrl('/product', { skipLocationChange: true })
      .then(() => {
        this.router.navigate([this.router.url]);
      });
  }

  onChangeFile(event: any, input: string) {
    if(event.target.files){
      switch(input){
        case 'avatar': {
          const file = event.target.files[0];
          this.avatar = file;
          console.log(this.avatar);
          this.avatarPreview = this.sanitizer.bypassSecurityTrustUrl(
            window.URL.createObjectURL(file)
          )
          break;
        }

        case 'img1': {
          const file = event.target.files[0];
          this.img1 = file;
          console.log(this.img1);
          this.img1Preview = this.sanitizer.bypassSecurityTrustUrl(
            window.URL.createObjectURL(file)
          )
          break;
        }

        case 'img2': {
          const file = event.target.files[0];
          this.img2 = file;
          console.log(this.img2);
          this.img2Preview = this.sanitizer.bypassSecurityTrustUrl(
            window.URL.createObjectURL(file)
          )
          break;
        }

        case 'img3': {
          const file = event.target.files[0];
          this.img3 = file;
          console.log(this.img3);
          this.img3Preview = this.sanitizer.bypassSecurityTrustUrl(
            window.URL.createObjectURL(file)
          )
          break;
        }

      }          
  }
}

  mapToInput(product: Product) {
    this.proudctInput.category_id = product.category_id;
    this.proudctInput.product_name = product.product_name;
    this.proudctInput.price = product.price;
    this.proudctInput.description = product.description;
    this.proudctInput.quantity = product.quantity;
    this.proudctInput.discount = product.discount;
    this.proudctInput.created_by = product.created_by;
    this.proudctInput.updated_by = product.updated_by;
  }

}
