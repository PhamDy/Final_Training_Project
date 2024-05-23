package tasc.finalproject.ProductService.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasc.finalproject.ProductService.entity.Category;
import tasc.finalproject.ProductService.repository.DaoCategoryRepository;
import tasc.finalproject.ProductService.repository.DaoProductRepository;
import tasc.finalproject.ProductService.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private DaoProductRepository productRepository;

    @Autowired
    private DaoCategoryRepository categoryRepository;

    @Override
    public long saveCategory(String name) {
        Category category = new Category();
        category.setName(name);
        category.setCreated_by("customer");
        return productRepository.saveProduct(category);
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.getCategory();
    }

    @Override
    public Category getCategoryById(long categoryId) {
        return categoryRepository.getCategoryById(categoryId);
    }

    @Override
    public void editCategoryById(long id, String name) {
        categoryRepository.editCategoryById(id, name);
    }

}
