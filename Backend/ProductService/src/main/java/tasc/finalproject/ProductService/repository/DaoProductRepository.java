package tasc.finalproject.ProductService.repository;

import tasc.finalproject.ProductService.entity.Category;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.ProductsResponse;

import java.util.List;

public interface DaoProductRepository {

    List<ProductsResponse> getProductAll();

    Product getProductById(long productId);

    long saveProduct(Category category);

}
