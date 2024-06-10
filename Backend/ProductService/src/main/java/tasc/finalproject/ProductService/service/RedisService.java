package tasc.finalproject.ProductService.service;

import tasc.finalproject.ProductService.entity.Product;

import java.util.List;

public interface RedisService {

    void set(String key, Object object);

    <T> T getKey(String key, Class<T> tClass);

    boolean hasKey(String key);

    void deleteKey(String key);

    void deleteAll();

    void deleteByPrefix(String prefix);

    // List cache
    void setList(String key, Object object);

    void setListProduct(String key, List<Product> list);

    <T> T getAfterDelete(String key, Class<T> tClass);

    boolean checkSizeList(String key);

}
