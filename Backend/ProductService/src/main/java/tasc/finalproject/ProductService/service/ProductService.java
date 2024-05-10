package tasc.finalproject.ProductService.service;

import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.ProductsResponse;

import java.util.List;

public interface ProductService {

    List<ProductsResponse> getProductAll();

    Product getProductById(long productId);
}
