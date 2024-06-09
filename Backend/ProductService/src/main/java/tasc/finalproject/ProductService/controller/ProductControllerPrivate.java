package tasc.finalproject.ProductService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/saveProduct")
    public ResponseEntity<?> saveProduct(@ModelAttribute CreateProduct createProduct,
                                         @RequestParam(value = "avatar") MultipartFile avatar,
                                         @RequestParam(value = "img1") MultipartFile img1,
                                         @RequestParam(value = "img2", required = false) MultipartFile img2,
                                         @RequestParam(value = "img3", required = false) MultipartFile img3){
        return new ResponseEntity<>(productService.save(createProduct, avatar, img1, img2, img3), HttpStatus.CREATED);
    }

    @PutMapping("/editProduct/{id}")
    public ResponseEntity<?> editProduct(@PathVariable long id,
                                         @ModelAttribute CreateProduct createProduct,
                                         @RequestParam(value = "avatar") MultipartFile avatar,
                                         @RequestParam(value = "img1", required = false) MultipartFile img1,
                                         @RequestParam(value = "img2", required = false) MultipartFile img2,
                                         @RequestParam(value = "img3", required = false) MultipartFile img3){
        productService.edit(id ,createProduct, avatar, img1, img2, img3);
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/saveCategory")
    public ResponseEntity<Category> addCategory(@RequestBody Category category){
        categoryService.saveCategory(category);
        return new ResponseEntity<>( category, HttpStatus.CREATED);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<Category> editCategoryById(@PathVariable long id, @RequestBody Category category){
        categoryService.editCategoryById(id, category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Integer> deleteCategoryById(@PathVariable long id){
        return new ResponseEntity<Integer>(categoryService.deleteCategoryById(id), HttpStatus.OK);
    }
}
