package tasc.finalproject.ProductService.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ExecutorConfig {

    public static final int MAX_THREADS = 2;

    @Bean
    public ExecutorService executorService(){
        return Executors.newFixedThreadPool(MAX_THREADS);
    }

}
