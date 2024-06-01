package tasc.finalproject.ProductService.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.entity.ProductStatus;
import tasc.finalproject.ProductService.exception.ProductNotFoundException;
import tasc.finalproject.ProductService.model.CreateProduct;
import tasc.finalproject.ProductService.model.Page;
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
    public Page<ProductsResponse> getProductAll(String name, int size, int offset) {

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
        productRepository.listProduct(name, size, offset );
        return productRepository.listProduct(name, size, offset );

    }

    @Override
    public Product getProductById(long productId) {

        var product = productRepository.getProductById(productId);
        LOGGER.info(String.format("Get product by id: " + productId));
        return product;
    }

    @Override
    public long save(CreateProduct createProduct, MultipartFile avatar, MultipartFile img1, MultipartFile img2, MultipartFile img3) {
        Product product = mapToEntity(createProduct);
        product.setAvatar(imageUploadService.uploadImage(avatar));
        product.setImg1(img1 != null ? imageUploadService.uploadImage(img1) : "");
        product.setImg2(img2 != null ? imageUploadService.uploadImage(img2) : "");
        product.setImg3(img3 != null ? imageUploadService.uploadImage(img3) : "");
        product.setCreated_by(createProduct.getCreated_by());
        product.setUpdated_by(createProduct.getUpdated_by());
        LOGGER.info(String.format("Save product successfully!"));
        LOGGER.info(String.format(product.toString()));
        return productRepository.saveProduct(product);
    }

    @Override
    public void edit(long id, CreateProduct createProduct, MultipartFile avatar,MultipartFile img1,MultipartFile img2,MultipartFile img3) {
        var check = productRepository.getProductById(id);
        if (check==null){
            throw new ProductNotFoundException("Not found product by id: " + id);
        }
        Product product = mapToEntity(createProduct);
        product.setAvatar(imageUploadService.uploadImage(avatar));
        product.setImg1(img1 != null ? imageUploadService.uploadImage(img1) : "");
        product.setImg2(img2 != null ? imageUploadService.uploadImage(img2) : "");
        product.setImg3(img3 != null ? imageUploadService.uploadImage(img3) : "");
        product.setUpdated_by(createProduct.getUpdated_by());
        product.setCreated_by(createProduct.getCreated_by());
        productRepository.editProduct(id, product);
        LOGGER.info(String.format("Edit product successfully!"));
    }

    public Product mapToEntity(CreateProduct createProduct){
        return Product
                .builder()
                .product_name(createProduct.getProduct_name())
                .category_id(createProduct.getCategory_id())
                .description(createProduct.getDescription())
                .price(createProduct.getPrice())
                .status(createProduct.getQuantity() != 0 ? ProductStatus.InStock : ProductStatus.OutOfStock)
                .discount(createProduct.getDiscount())
                .quantity(createProduct.getQuantity())
                .build();
    }
}
