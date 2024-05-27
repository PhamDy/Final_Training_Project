package tasc.finalproject.ProductService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tasc.finalproject.ProductService.entity.Category;
import tasc.finalproject.ProductService.model.CreateProduct;
import tasc.finalproject.ProductService.service.CategoryService;
import tasc.finalproject.ProductService.service.ProductService;

@RestController
@RequestMapping("/private/api/v1/product")
@CrossOrigin(origins = "*",maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:4200")
public class ProductControllerPrivate {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('User') || hasRole('Admin')")
    @GetMapping("/user")
    public ResponseEntity<String> testUser(){
        return new ResponseEntity<>("test Product private User", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/admin")
    public ResponseEntity<String> testAdmin(){
        return new ResponseEntity<>("test Product private Admin", HttpStatus.OK);
    }

    @PostMapping("/saveProduct")
    public ResponseEntity<?> saveProduct(@ModelAttribute CreateProduct createProduct){
        return new ResponseEntity<>(productService.save(createProduct), HttpStatus.CREATED);
    }

    @PutMapping("/editProduct/{id}")
    public ResponseEntity<?> editProduct(@PathVariable long id, @ModelAttribute CreateProduct createProduct){
        productService.edit(id ,createProduct);
        return new ResponseEntity<>( HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/saveCategory")
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        categoryService.saveCategory(category);
        return new ResponseEntity<>( "Ok", HttpStatus.CREATED);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<String> editCategoryById(@PathVariable long id, @RequestBody Category category){
        categoryService.editCategoryById(id, category);
        return new ResponseEntity<>("Edit category successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Integer> deleteCategoryById(@PathVariable long id){
        return new ResponseEntity<Integer>(categoryService.deleteCategoryById(id), HttpStatus.OK);
    }
}
