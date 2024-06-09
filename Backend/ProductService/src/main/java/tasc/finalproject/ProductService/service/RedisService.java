package tasc.finalproject.ProductService.service;

public interface RedisService {

    void set(String key, Object object);

    <T> T getKey(String key, Class<T> tClass);

    boolean hasKey(String key);

    void deleteKey(String key);

    void deleteAll();

    void deleteByPrefix(String prefix);

}
