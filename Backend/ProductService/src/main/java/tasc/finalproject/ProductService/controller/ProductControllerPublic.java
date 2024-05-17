package tasc.finalproject.ProductService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.ProductsResponse;
import tasc.finalproject.ProductService.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/public/api/v1/product")
public class ProductControllerPublic {

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductsResponse>> getProductAll(){
        return new ResponseEntity<>(productService.getProductAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductAll(@PathVariable long id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }



}
