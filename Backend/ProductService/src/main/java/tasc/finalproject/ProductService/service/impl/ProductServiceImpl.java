package tasc.finalproject.ProductService.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static tasc.finalproject.ProductService.schedule.CacheProduct.keyCategory;
import static tasc.finalproject.ProductService.schedule.CacheProduct.keyProductDetails;


@Service
public class ProductServiceImpl implements ProductService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private DaoProductRepository productRepository;
    private ImageUploadService imageUploadService;
    private RedisService redisService;

    public ProductServiceImpl(DaoProductRepository productRepository, ImageUploadService imageUploadService, RedisService redisService) {
        this.productRepository = productRepository;
        this.imageUploadService = imageUploadService;
        this.redisService = redisService;
    }

    public Page<ProductsResponse> getProductAll(String name, List<Long> category, int size, int offset) {
        return productRepository.listProduct(name, category, size, offset );
    }

    @Override
    public Product getProductById(long productId) {
        String redisKey = keyProductDetails + productId;
        Product product;
        product = redisService.getKey(redisKey, Product.class);
        if (product == null){
            product = productRepository.getProductById(productId);
            LOGGER.info("Get product by id successfully (Redis) " + productId);
            redisService.set(redisKey, product);
            return  product;
        }
        return product;
    }

    @Override
    public List<Product> getListRelatedProduct(long productId) {
        long categoryId = productRepository.categoryIdByProductId(productId);

        var productIdList = ((List<Integer>) redisService.lindex(keyCategory+categoryId, 0));
        if (productIdList==null){
            var list = productRepository.getProductByCategoryId(categoryId);
            return list;
        }
        LOGGER.info(productIdList.toString());

        Collections.shuffle(productIdList);
        List<Product> listRelatedProduct = new ArrayList<>();

        productIdList.subList(0, 4).forEach(id ->
                    listRelatedProduct.add(redisService.getKey(keyProductDetails+id, Product.class))
                );
        return listRelatedProduct;
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
