package tasc.finalproject.ProductService.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.entity.ProductStatus;
import tasc.finalproject.ProductService.exception.ProductNotFoundException;
import tasc.finalproject.ProductService.model.CreateProduct;
import tasc.finalproject.ProductService.model.ProductsResponse;
import tasc.finalproject.ProductService.repository.DaoProductRepository;
import tasc.finalproject.ProductService.service.ImageUploadService;
import tasc.finalproject.ProductService.service.ProductService;
import tasc.finalproject.ProductService.service.RedisService;

@Service
public class ProductServiceImpl implements ProductService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private DaoProductRepository productRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private RedisService redisService;

    @Override
    public Page<ProductsResponse> getProductAll(Pageable pageable) {

//        String redisKey = "List product";
//        List<ProductsResponse> productList = redisService.getListProduct(redisKey);
//
//        if (productList != null) {
//            LOGGER.info("Get list product from Redis successfully ... !");
//            return productList;
//        }
//
//        LOGGER.info("Get list product successfully !");
//        productList = productRepository.getProductAll();
//
//        redisService.setListProduct(redisKey, productList);
//
//        return productList;
        var list = productRepository.listProduct(pageable);
        return new PageImpl<>(list.getContent(), list.getPageable(), list.getTotalPages());

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

    @Override
    public long save(CreateProduct createProduct) {
        Product product = mapToEntity(createProduct);
        product.setCreated_by(createProduct.getCreated_by());
        LOGGER.info(String.format("Save product successfully!"));
        return productRepository.saveProduct(product);
    }

    @Override
    public void edit(long id, CreateProduct createProduct) {
        var check = productRepository.getProductById(id);
        if (check==null){
            throw new ProductNotFoundException("Not found product by id: " + id);
        }
        Product product = mapToEntity(createProduct);
        product.setUpdated_by(createProduct.getCreated_by());
        productRepository.editProduct(id, product);
        LOGGER.info(String.format("Edit product successfully!"));
    }

    public Product mapToEntity(CreateProduct createProduct){
        return Product
                .builder()
                .product_name(createProduct.getProduct_name())
                .category_id(createProduct.getCategory_id())
                .avatar(imageUploadService.uploadImage(createProduct.getAvatar()))
                .img1(createProduct.getImg1() != null ? imageUploadService.uploadImage(createProduct.getImg1()) : "")
                .img2(createProduct.getImg2() != null ? imageUploadService.uploadImage(createProduct.getImg2()) : "")
                .img3(createProduct.getImg3() != null ? imageUploadService.uploadImage(createProduct.getImg3()) : "")
                .description(createProduct.getDescription())
                .price(createProduct.getPrice())
                .status(createProduct.getQuantity() != 0 ? ProductStatus.InStock : ProductStatus.OutOfStock)
                .discount(createProduct.getDiscount())
                .quantity(createProduct.getQuantity())
                .build();
    }
}
