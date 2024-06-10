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
import tasc.finalproject.ProductService.service.thread.ThreadCacheProductAll;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@EnableScheduling
public class ProductServiceImpl implements ProductService {

    public static final int NUM_THREAD = 2;
    public final static int size = 10;
    public static final String keyQueueProduct = "ProductService:queueProduct";
    public static final String keyProductDetails = "ProductService:productDetails:";
    public static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private DaoProductRepository productRepository;
    private ImageUploadService imageUploadService;
    private RedisService redisService;

    public ProductServiceImpl(DaoProductRepository productRepository, ImageUploadService imageUploadService, RedisService redisService) {
        this.productRepository = productRepository;
        this.imageUploadService = imageUploadService;
        this.redisService = redisService;
    }

//    @Scheduled(cron = "0 0 16 * * ?")
//    @Scheduled(fixedRate = 5000)
    public void initCacheAll(){
        LOGGER.info("Start...");
        autoSaveProductAll();
        int offset = 0;
        while (true){
            var list = productRepository.listProduct(size, offset);
            if (list==null || list.isEmpty()){
                break;
            }
            redisService.setListProduct(keyQueueProduct, list);
            offset+=size;
        }
        LOGGER.info("Cache initialization finished.");
    }

    @Scheduled(fixedRate = 5000)
    public void initCheckUpdate(){
        LOGGER.info("Start check product update");
        var lastRequest = productRepository.lastRequestProduct();
        if (productRepository.checkProductUpdate()){
            var list = productRepository.listProductUpdate(lastRequest);
            list.forEach(product -> redisService.set(keyProductDetails+product.getProduct_id(), product));
            productRepository.updateLastRequestProduct();
            LOGGER.info("Update product details on Redis cache successfully! " + keyProductDetails);
        }
        LOGGER.info("Not found Product update");
    }

    // Đa tiến trình
    private void autoSaveProductAll() {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREAD);
        for (int i = 1; i <= NUM_THREAD; i++) {
            executorService.submit(new ThreadCacheProductAll(redisService));
        }
    }

    public Page<ProductsResponse> getProductAll(String name, int size, int offset) {
        productRepository.listProduct(name, size, offset );
        return productRepository.listProduct(name, size, offset );

    }

    @Override
    public Product getProductById(long productId) {
        String redisKey = keyProductDetails + productId;
        Product product;
        product = redisService.getKey(redisKey, Product.class);

        if (product != null){
            LOGGER.info("Get product by id successfully (Redis) " + productId);
            return product;
        }

        product = productRepository.getProductById(productId);
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
