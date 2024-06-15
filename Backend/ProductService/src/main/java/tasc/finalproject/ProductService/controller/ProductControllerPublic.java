package tasc.finalproject.ProductService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasc.finalproject.ProductService.entity.Category;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.FilterDto;
import tasc.finalproject.ProductService.model.Page;
import tasc.finalproject.ProductService.model.ProductsResponse;
import tasc.finalproject.ProductService.repository.DaoProductRepository;
import tasc.finalproject.ProductService.service.CategoryService;
import tasc.finalproject.ProductService.service.ProductService;
import tasc.finalproject.ProductService.service.RedisService;

import java.time.LocalDateTime;
import java.util.List;

import static tasc.finalproject.ProductService.schedule.CacheProduct.*;

@RestController
@RequestMapping("/public/api/v1/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",maxAge = 3600)
public class ProductControllerPublic {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final RedisService redisService;
    private final DaoProductRepository productRepository;

    @GetMapping("/")
    public ResponseEntity<Page<ProductsResponse>> getProductAll(@RequestBody(required = false) FilterDto dto,
                                                                @RequestParam int size,
                                                                @RequestParam int offset) {
        return new ResponseEntity<>(productService.getProductAll(dto.getName(), dto.getCategory(), size, offset), HttpStatus.OK);
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
        redisService.deleteByPrefix(keyProductDetails);
        return "Ok";
    }

    @DeleteMapping("/deleteAllCache")
    public String deleteAllCache(){
        redisService.deleteAll();
        return "OK";
    }

    @GetMapping("/product/lastUpdate")
    public ResponseEntity<List<Product>> listCheckUpdate(){
//        var time1 = convertStringToLocalDateTime(time);
        var time1 = LocalDateTime.now();
        if (productRepository.checkProductUpdate()){
            return new ResponseEntity<>(productRepository.listProductUpdate(time1), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/realtedProduct/{id}")
    public ResponseEntity<List<Product>> listRelated(@PathVariable long id){
        return new ResponseEntity<>(productService.getListRelatedProduct(id), HttpStatus.OK);
    }


}
