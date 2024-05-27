package tasc.finalproject.ProductService.repository;

import tasc.finalproject.ProductService.entity.Category;

import java.util.List;

public interface DaoCategoryRepository {

    List<Category> getCategory();

    Category getCategoryById(long categoryId);

    long saveCategory(Category category);

    void editCategoryById(long id, Category category);

    int deleteCategoryById(long id);


}
