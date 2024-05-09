package tasc.finalproject.ProductService.repository;

import tasc.finalproject.ProductService.entity.Product;

import java.util.List;

public interface DaoProductRepository {

    List<Product> getProductAll();

}
