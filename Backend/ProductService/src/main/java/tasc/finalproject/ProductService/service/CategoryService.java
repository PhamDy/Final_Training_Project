package tasc.finalproject.ProductService.service;

import tasc.finalproject.ProductService.entity.Category;

import java.util.List;

public interface CategoryService {
    long saveCategory(String name);

    List<Category> getAll();

    Category getCategoryById(long categoryId);
}
