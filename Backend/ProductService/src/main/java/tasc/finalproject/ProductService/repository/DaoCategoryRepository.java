package tasc.finalproject.ProductService.repository;

import tasc.finalproject.ProductService.entity.Category;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.ProductsResponse;

import java.util.List;

public interface DaoCategoryRepository {

    List<Category> getCategory();

    Category getCategoryById(long categoryId);

    long saveProduct(Category category);

    void editCategoryById(long id, String name);


}
