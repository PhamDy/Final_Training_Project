package tasc.finalproject.ProductService.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.exception.ProductNotFoundException;
import tasc.finalproject.ProductService.model.ProductsResponse;
import tasc.finalproject.ProductService.repository.DaoProductRepository;
import tasc.finalproject.ProductService.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private DaoProductRepository productRepository;


    @Override
    public List<ProductsResponse> getProductAll() {
        LOGGER.info(String.format("Get list product successfully ... !"));
        return productRepository.getProductAll();
    }

    @Override
    public Product getProductById(long productId) {
        Product product = productRepository.getProductById(productId);
        LOGGER.info(String.format("Get product by id: " + productId));
        return product;
    }
}
