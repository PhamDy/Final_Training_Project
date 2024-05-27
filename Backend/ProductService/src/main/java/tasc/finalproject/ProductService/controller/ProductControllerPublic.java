package tasc.finalproject.ProductService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasc.finalproject.ProductService.entity.Category;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.ProductsResponse;
import tasc.finalproject.ProductService.service.CategoryService;
import tasc.finalproject.ProductService.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/public/api/v1/product")
@CrossOrigin(origins = "*",maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:4200")
public class ProductControllerPublic {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<Page<ProductsResponse>> getProductAll(@PageableDefault(size = 10)Pageable pageable) {
        return new ResponseEntity<>(productService.getProductAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductAll(@PathVariable long id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategoryAll(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable long id){
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }



}
