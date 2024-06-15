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
    void setListProducts(String key, Object object);

    void setListProducts(String key, List<Product> list);

    void setListCategoryId(String key, List<Long> list);

    Object lindex(String key, long index);

    <T> T getAfterDeleteListProduct(String key, Class<T> tClass);

     List<Product> getAfterDeleteListProduct(String key);

    List<Object> getListByRange(String key, long start, long end);

    boolean checkSizeList(String key);

}
