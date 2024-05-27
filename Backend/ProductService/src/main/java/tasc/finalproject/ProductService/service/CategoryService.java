package tasc.finalproject.ProductService.service;

import tasc.finalproject.ProductService.entity.Category;

import java.util.List;

public interface CategoryService {
    long saveCategory(Category category);

    List<Category> getAll();

    Category getCategoryById(long categoryId);

    void editCategoryById(long id, Category category);

    int deleteCategoryById(long id);
}
