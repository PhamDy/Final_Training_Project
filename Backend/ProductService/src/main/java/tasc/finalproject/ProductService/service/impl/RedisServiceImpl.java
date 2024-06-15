package tasc.finalproject.ProductService.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.service.RedisService;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RedisServiceImpl implements RedisService {

    private RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOperations;
    private ListOperations<String, Object> listOperations;
    private ObjectMapper objectMapper;

    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.listOperations = redisTemplate.opsForList();
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void set(String key, Object object) {
        try {
            valueOperations.set(key, object);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing object to JSON", e);
        }
    }

    @Override
    public <T> T getKey(String key, Class<T> tClass) {
        try {
            Object object = valueOperations.get(key);
            return objectMapper.convertValue(object, tClass);

        } catch (Exception e) {
            throw new RuntimeException("Error retrieving object from Redis", e);
        }
    }

    @Override
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            throw new RuntimeException("Error checking key existence in Redis", e);
        }
    }

    @Override
    public void deleteKey(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting key from Redis", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            Set<String> keys = redisTemplate.keys("*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting all keys from Redis", e);
        }
    }

    @Override
    public void deleteByPrefix(String prefix) {
        try {
            Set<String> keys = redisTemplate.keys(prefix + "*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting keys with prefix from Redis", e);
        }
    }

    @Override
    public void setListProducts(String key, Object object) {
        try {
            listOperations.leftPush(key, object);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setListProducts(String key, List<Product> list) {
        try {
//            list.stream().forEach(item -> listOperations.rightPush(key, item));
            redisTemplate.opsForList().rightPushAll(key,list);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setListCategoryId(String key, List<Long> list) {
        try {
            if (hasKey(key)){
                deleteKey(key);
            }
            listOperations.rightPushAll(key ,list);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Object lindex(String key, long index) {
        try {
            return listOperations.index(key, index);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public <T> T getAfterDeleteListProduct(String key, Class<T> tClass) {
        try {
            if (hasKey(key)){
                deleteKey(key);
            }
            var product = listOperations.leftPop(key, Duration.ofSeconds(5));
            return objectMapper.convertValue(product, tClass);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Product> getAfterDeleteListProduct(String key) {
        try {
            Object obj = listOperations.leftPop(key);
            if (obj instanceof List) {
                List<Object> rawList = (List<Object>) obj;
                List<Product> products = rawList.stream()
                        .map(item -> objectMapper.convertValue(item, Product.class))
                        .collect(Collectors.toList());
                return products;
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Object> getListByRange(String key, long start, long end) {
        try {
            var list = listOperations.range(key, start, end);
            return list;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean checkSizeList(String key) {
        return listOperations.size(key)!=0;
    }


}
