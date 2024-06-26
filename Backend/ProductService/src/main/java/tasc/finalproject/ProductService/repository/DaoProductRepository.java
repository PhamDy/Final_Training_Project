package tasc.finalproject.ProductService.repository;

import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.Page;
import tasc.finalproject.ProductService.model.ProductsResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface DaoProductRepository {

    List<ProductsResponse> getProductAll();

    Product getProductById(long productId);

    List<Product> getProductByCategoryId(long categoryId);

    long saveProduct(Product product);

    void editProduct(long productId ,Product product);

    Page<ProductsResponse> listProduct(String name, List<Long> category_id, int size, int offset);

    List<Product> listProduct(int size, int offset);

    LocalDateTime lastRequestProduct();

    void updateLastRequestProduct();

    List<Product> listProductUpdate(LocalDateTime lastRequest);

    boolean checkProductUpdate();

    List<Long> listProductIdByCategory(long categoryId);

    long categoryIdByProductId(long productId);

}
