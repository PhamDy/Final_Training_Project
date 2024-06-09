package tasc.finalproject.ProductService.service.thread;

import lombok.SneakyThrows;
import tasc.finalproject.ProductService.service.RedisService;
import tasc.finalproject.ProductService.service.impl.ProductServiceImpl;

public class CacheProductAll implements Runnable{

    private final RedisService redisService;

    private static String key = "offline:product:";

    public CacheProductAll(RedisService redisService) {
        this.redisService = redisService;
    }

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(2000);
            System.out.println("start thread ... " + Thread.currentThread().getId());
            if (ProductServiceImpl.productBlockingQueue!=null){
                while (!ProductServiceImpl.productBlockingQueue.isEmpty()){
                    var product = ProductServiceImpl.productBlockingQueue.take();
                    redisService.set(key + product.getProduct_id(), product);
                }
            }
            System.out.println(Thread.currentThread().getId() + " fineshed!");
    }

}
