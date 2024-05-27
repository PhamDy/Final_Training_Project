package tasc.finalproject.ProductService.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.CreateProduct;
import tasc.finalproject.ProductService.model.ProductsResponse;

public interface ProductService {

    Page<ProductsResponse> getProductAll(Pageable pageable);

    Product getProductById(long productId);

    long save(CreateProduct product);

    void edit(long id ,CreateProduct product);
}
