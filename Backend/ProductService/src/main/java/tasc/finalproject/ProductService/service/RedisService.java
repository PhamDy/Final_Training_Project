package tasc.finalproject.ProductService.service;

import tasc.finalproject.ProductService.model.ProductsResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {

    void set(String key, String value);

    void set(String key, Object object);

    void hashSet(String key, String field, Object value);

    boolean hashExists(String key, String field);

    Object getKey(String key);

    Map<String, Object> getField(String key);

    Object hashGet(String key, String field);

    List<Object> hashGetByFieldPrefix(String key, String fieldPrefix);

    Set<String> getFieldPrefixes(String key);

    void deleteHashSet(String key);

    void deleteHashSet(String key, String field);

    void deleteHashSet(String key, List<String> fields);

    void setListProduct(String key, List<ProductsResponse> list);

    List<ProductsResponse> getListProduct(String key);

}
