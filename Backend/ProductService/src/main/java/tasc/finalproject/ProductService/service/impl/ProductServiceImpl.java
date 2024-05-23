package tasc.finalproject.ProductService.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.exception.ProductNotFoundException;
import tasc.finalproject.ProductService.model.ProductsResponse;
import tasc.finalproject.ProductService.repository.DaoProductRepository;
import tasc.finalproject.ProductService.service.ProductService;
import tasc.finalproject.ProductService.service.RedisService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private DaoProductRepository productRepository;

    @Autowired
    private RedisService redisService;

    @Override
    public List<ProductsResponse> getProductAll() {

        String redisKey = "List product";
        List<ProductsResponse> productList = redisService.getListProduct(redisKey);

        if (productList != null) {
            LOGGER.info("Get list product from Redis successfully ... !");
            return productList;
        }

        LOGGER.info("Get list product successfully !");
        productList = productRepository.getProductAll();

        redisService.setListProduct(redisKey, productList);

        return productList;
    }

    @Override
    public Product getProductById(long productId) {
        String redisKey = "Product details";
        Product product = (Product) redisService.hashGet(redisKey, String.valueOf(productId));
        if (product != null){
            return product;
        }

        product = productRepository.getProductById(productId);
        LOGGER.info(String.format("Get product by id: " + productId));
        redisService.hashSet(redisKey, String.valueOf(productId), product);
        return product;
    }
}
