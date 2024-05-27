package tasc.finalproject.ProductService.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tasc.finalproject.ProductService.entity.Category;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.ProductsResponse;

import java.util.List;

public interface DaoProductRepository {

    List<ProductsResponse> getProductAll();

    Product getProductById(long productId);

    long saveProduct(Product product);

    void editProduct(long productId ,Product product);

    Page<ProductsResponse> listProduct(Pageable pageable);

}
