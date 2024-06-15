package tasc.finalproject.ProductService.schedule.thread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tasc.finalproject.ProductService.service.RedisService;

@RequiredArgsConstructor
@Slf4j
public class ThreadRelatedProducts implements Runnable{

    public final RedisService redisService;

    @Override
    public void run() {

    }
}
