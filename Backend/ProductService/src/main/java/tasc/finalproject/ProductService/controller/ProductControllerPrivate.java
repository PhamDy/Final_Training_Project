package tasc.finalproject.ProductService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tasc.finalproject.ProductService.service.CategoryService;
import tasc.finalproject.ProductService.service.ProductService;

@RestController
@RequestMapping("/private/api/v1/product")
public class ProductControllerPrivate {

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
    @PostMapping("/saveCategory")
    public ResponseEntity<String> addCategory(@RequestParam String name){
        categoryService.saveCategory(name);
        return new ResponseEntity<>( "Ok", HttpStatus.CREATED);
    }
}
