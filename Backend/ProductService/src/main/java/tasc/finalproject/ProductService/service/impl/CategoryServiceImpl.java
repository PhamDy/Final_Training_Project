package tasc.finalproject.ProductService.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasc.finalproject.ProductService.entity.Category;
import tasc.finalproject.ProductService.repository.DaoProductRepository;
import tasc.finalproject.ProductService.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private DaoProductRepository productRepository;

    @Override
    public long saveCategory(String name) {
        Category category = new Category();
        category.setName(name);
        category.setCreated_by("customer");
        return productRepository.saveProduct(category);
    }
}
