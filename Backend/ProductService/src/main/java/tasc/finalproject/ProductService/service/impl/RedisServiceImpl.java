package tasc.finalproject.ProductService.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tasc.finalproject.ProductService.entity.Product;
import tasc.finalproject.ProductService.model.ProductsResponse;
import tasc.finalproject.ProductService.service.RedisService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, Object> hashOperations;
    private ObjectMapper objectMapper;

    public RedisServiceImpl(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setTimeToLive(String key, long timeOutInDays) {
        redisTemplate.expire(key, timeOutInDays, TimeUnit.DAYS);
    }

    @Override
    public void hashSet(String key, String field, Object value) {
        try {
            String jsonString = objectMapper.writeValueAsString(value);
            hashOperations.put(key, field, jsonString);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing object to JSON", e);
        }
    }

    @Override
    public boolean hashExists(String key, String field) {
        return hashOperations.hasKey(key, field);
    }

    @Override
    public Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Map<String, Object> getField(String key) {
        return hashOperations.entries(key);
    }

    @Override
    public Object hashGet(String key, String field) {
        try {
            String jsonString = (String) hashOperations.get(key, field);
            if (jsonString == null) {
                return null;
            }
            return objectMapper.readValue(jsonString, Product.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON to object", e);
        }
    }

    @Override
    public List<Object> hashGetByFieldPrefix(String key, String fieldPrefix) {
        List<Object> objects = new ArrayList<>();

        Map<String, Object> hashEntries = hashOperations.entries(key);
        for (Map.Entry<String, Object> entry: hashEntries.entrySet()
             ) {
            objects.add(entry.getValue());
        }
        return objects;
    }

    @Override
    public Set<String> getFieldPrefixes(String key) {
        return hashOperations.entries(key).keySet();
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delete(String key, String field) {
        hashOperations.delete(key, field);
    }

    @Override
    public void delete(String key, List<String> fields) {
        for (String field: fields
             ) {
            hashOperations.delete(key, field);
        }
    }

    @Override
    public void setListProduct(String key, List<ProductsResponse> list) {
        try {
            String value = objectMapper.writeValueAsString(list);
            set(key, value);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductsResponse> getListProduct(String key) {
        try {
            String jsonString = (String) getKey(key);
            if (jsonString == null) {
                return null;
            }
            return objectMapper.readValue(jsonString, new TypeReference<List<ProductsResponse>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing JSON to list", e);
        }
    }

}
