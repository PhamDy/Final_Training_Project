package tasc.finalproject.ProductService.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
import tasc.finalproject.ProductService.service.thread.CacheProductAll;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@EnableScheduling
public class ProductServiceImpl implements ProductService {

    public static final int NUM_THREAD = 3;
    final int size = 10;
    public static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    private DaoProductRepository productRepository;
    private ImageUploadService imageUploadService;
    private RedisService redisService;

    public ProductServiceImpl(DaoProductRepository productRepository, ImageUploadService imageUploadService, RedisService redisService) {
        this.productRepository = productRepository;
        this.imageUploadService = imageUploadService;
        this.redisService = redisService;
    }

    public static BlockingQueue<Product> productBlockingQueue = new LinkedBlockingQueue<>();

//    @Scheduled(cron = "0 0 16 * * ?")
    @Scheduled(fixedRate = 5000)
    public void initCacheAll(){
        System.out.println("Start");
        autoSaveProductAll();
        int offset = 0;
        while (true){
            var list = productRepository.listProduct(size, offset);
            if (list==null || list.isEmpty()){
                break;
            }
            productBlockingQueue.addAll(list);
            offset+=size;
        }
    }

    // Đa tiến trình
    private void autoSaveProductAll() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREAD);
        for (int i = 1; i <= NUM_THREAD; i++) {
            executorService.submit(new CacheProductAll(redisService));
        }
    }

    public Page<ProductsResponse> getProductAll(String name, int size, int offset) {
        productRepository.listProduct(name, size, offset );
        return productRepository.listProduct(name, size, offset );

    }

    @Override
    public Product getProductById(long productId) {
        String redisKey = "offline:product:" + productId;
        Product product;
        product = redisService.getKey(redisKey, Product.class);

        if (product != null){
            LOGGER.info("Get product by id successfully (Redis) " + productId);
            return product;
        }

        product = productRepository.getProductById(productId);
        if (product!=null){
            redisService.set(redisKey, product);
            LOGGER.info("Set product by id Redis successfully " + productId);

            return product;
        }
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
