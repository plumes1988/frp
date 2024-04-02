package com.figure.core.redis;

import java.util.List;
import java.util.Map;

public interface IRedisTemplateService {

    boolean lock(String key);

    void unlock(String key);

    void setRedisCache(String cacheName, Object obj);

    <T> T getObjectRedisCache(String cacheName, Class<T> c);

    <T> List<T> getListRedisCache(String cacheName, Class<T> c);

    <K, V> Map<K, V> getMapRedisByKeyCache(String cacheName, Class<K> key, Class<V> value);

    <K, V> Map<K, List<V>> getMapRedisByKeyCacheForValueIsList(String cacheName, Class<K> key, Class<V> value);

    void sendToQueue(String queueName,Object msgObj);

}
