package tasc.finalproject.ProductService.schedule.thread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.service.RedisService;

import static tasc.finalproject.ProductService.schedule.CacheProduct.keyProductDetails;
import static tasc.finalproject.ProductService.schedule.CacheProduct.keyQueueProduct;


@RequiredArgsConstructor
@Slf4j
public class ThreadCacheProductAll implements Runnable{

    private final RedisService redisService;

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            var start = System.currentTimeMillis();
        log.info("start thread ... " + Thread.currentThread().getId());
            while (redisService.checkSizeList(keyQueueProduct)){
                var list = redisService.getAfterDeleteListProduct(keyQueueProduct);
                if (list!=null && !list.isEmpty()){
                    list.forEach(product -> redisService.set(keyProductDetails+product.getProduct_id(), product));
                }
            }
            log.info(Thread.currentThread().getId() + " finished! - Time finished: " + (System.currentTimeMillis()-start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
