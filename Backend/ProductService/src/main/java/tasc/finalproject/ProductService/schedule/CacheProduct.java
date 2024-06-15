package tasc.finalproject.ProductService.schedule;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tasc.finalproject.ProductService.repository.DaoCategoryRepository;
import tasc.finalproject.ProductService.repository.DaoProductRepository;
import tasc.finalproject.ProductService.schedule.thread.ThreadCacheProductAll;
import tasc.finalproject.ProductService.service.RedisService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Component
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class CacheProduct {

    static int size = 10;
    public static String keyQueueProduct = "ProductService:queueProduct";
    public static String keyProductDetails = "ProductService:productDetails:";
    public static String keyCategory = "ProductService:relatedProducts:";

    private final DaoProductRepository productRepository;
    private final DaoCategoryRepository categoryRepository;
    private final RedisService redisService;
    private final ExecutorService executorService;


//    @Scheduled(cron = "0 0 16 * * ?")
//    @Scheduled(fixedRate = 2000)
    public void initCacheProductsAll(){
        log.info("Start...");
        autoSaveProductAll();
        int offset = 0;
        while (true){
            var list = productRepository.listProduct(size, offset);
            if (list==null || list.isEmpty()){
                break;
            }
            redisService.setListProducts(keyQueueProduct, list);
            offset+=size;
        }
        log.info("Cache initialization finished.");
    }

    @PostConstruct
    public void test(){
        log.info("Start");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        redisService.setListProducts(keyQueueProduct, list);
        log.info("Successfully!");
    }

//    @PostConstruct
//    @Scheduled(fixedRate = 5000)
    public void inintCacheRelatedProducts(){
        log.info("Start...");
            var listCategoryId = categoryRepository.getCategoryId();
            listCategoryId.forEach(id ->
                    redisService.setListCategoryId(keyCategory + id, productRepository.listProductIdByCategory(id))
            );
        log.info("Successfully...");
    }

//    @Scheduled(fixedRate = 2000)
    public void initCheckUpdate(){
        log.info("Start check product update");
        var lastRequest = productRepository.lastRequestProduct();
        if (productRepository.checkProductUpdate()){
            var list = productRepository.listProductUpdate(lastRequest);
            list.forEach(product -> redisService.set(keyProductDetails+product.getProduct_id(), product));
            productRepository.updateLastRequestProduct();
            log.info("Update product details on Redis cache successfully! " + keyProductDetails);
        }
        log.info("Not found Product update");
    }

    // Đa tiến trình
    private void autoSaveProductAll() {
        executorService.submit(new ThreadCacheProductAll(redisService));
        executorService.submit(new ThreadCacheProductAll(redisService));
    }


}
