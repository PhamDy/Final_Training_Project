package tasc.finalproject.ProductService.service.thread;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.service.RedisService;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static tasc.finalproject.ProductService.service.impl.ProductServiceImpl.keyProductDetails;
import static tasc.finalproject.ProductService.service.impl.ProductServiceImpl.keyQueueProduct;

@Slf4j
public class ThreadCacheProductAll implements Runnable{

    private final RedisService redisService;

    public ThreadCacheProductAll(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            var start = System.currentTimeMillis();
        log.info("start thread ... " + Thread.currentThread().getId());
                while (redisService.checkSizeList(keyQueueProduct)){
                    var product =  redisService.getAfterDelete(keyQueueProduct, Product.class);
                    if (product!=null){
                        redisService.set(keyProductDetails + product.getProduct_id(), product);
                    }
                }
            log.info(Thread.currentThread().getId() + " finished! - Time finished: " + (System.currentTimeMillis()-start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
