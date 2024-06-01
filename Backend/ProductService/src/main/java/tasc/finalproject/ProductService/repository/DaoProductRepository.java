package tasc.finalproject.ProductService.repository;

import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.Page;
import tasc.finalproject.ProductService.model.ProductsResponse;

import java.util.List;

public interface DaoProductRepository {

    List<ProductsResponse> getProductAll();

    Product getProductById(long productId);

    long saveProduct(Product product);

    void editProduct(long productId ,Product product);

    Page<ProductsResponse> listProduct(String name, int size, int offset) ;

}
