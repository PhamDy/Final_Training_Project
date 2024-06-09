package tasc.finalproject.ProductService.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasc.finalproject.ProductService.entity.Category;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.Page;
import tasc.finalproject.ProductService.model.ProductsResponse;
import tasc.finalproject.ProductService.service.CategoryService;
import tasc.finalproject.ProductService.service.ProductService;
import tasc.finalproject.ProductService.service.RedisService;

import java.util.List;

@RestController
@RequestMapping("/public/api/v1/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",maxAge = 3600)
public class ProductControllerPublic {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final RedisService redisService;


    @GetMapping("/")
    public ResponseEntity<Page<ProductsResponse>> getProductAll(@RequestParam(required = false) String name,
                                                                @RequestParam int size,
                                                                @RequestParam int offset) {
        return new ResponseEntity<>(productService.getProductAll(name, size , offset), HttpStatus.OK);
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

    @DeleteMapping("/deleteKeyCache/{key}")
    public String deleteKeyCache(@PathVariable String key){
        redisService.deleteKey(key);
        return "OK";
    }

    @DeleteMapping("/prefix")
    public String deleteProductsByPrefix() {
        redisService.deleteByPrefix("offline:product:");
        return "Ok";
    }

    @DeleteMapping("/deleteAllCache")
    public String deleteAllCache(){
        redisService.deleteAll();
        return "OK";
    }



}
