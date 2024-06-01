package tasc.finalproject.ProductService.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.CreateProduct;
import tasc.finalproject.ProductService.model.Page;
import tasc.finalproject.ProductService.model.ProductsResponse;

public interface ProductService {

    Page<ProductsResponse> getProductAll(String name, int size, int offset);

    Product getProductById(long productId);

    long save(CreateProduct product, MultipartFile avatar,MultipartFile img1,MultipartFile img2,MultipartFile img3);

    void edit(long id ,CreateProduct product, MultipartFile avatar,MultipartFile img1,MultipartFile img2,MultipartFile img3);
}
