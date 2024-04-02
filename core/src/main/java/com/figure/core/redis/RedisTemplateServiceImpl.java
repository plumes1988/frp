package com.figure.core.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.figure.core.redis.RedisConstants.QUEUE_NAME_SAVE_INDICATOR_HISTORY_FOR_ALARM;

@Service
public class RedisTemplateServiceImpl implements IRedisTemplateService {
    private static final long LOCK_EXPIRE_TIME = 10; // 锁的过期时间
    private static final long LOCK_WAIT_TIME = 1000;    // 获取锁的最大等待时间，单位毫秒
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private MessageProducer messageProducer;

    public boolean lock(String key) {
        long startTime = System.currentTimeMillis();
        try {
            while (true) {
                // 尝试获取锁
                Boolean acquired = redisTemplate.opsForValue().setIfAbsent(key, "locked", LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
                if (acquired != null && acquired) {
                    // 成功获取到锁
                    return true;
                }
                // 未获取到锁，等待一段时间后重试
                if (System.currentTimeMillis() - startTime > LOCK_WAIT_TIME) {
                    // 获取锁超时
                    return false;
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public void unlock(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void setRedisCache(String cacheName, Object obj) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(cacheName, JSON.toJSONString(obj));
    }

    @Override
    public <T> T getObjectRedisCache(String cacheName, Class<T> c) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations ops = redisTemplate.opsForValue();
        Object jsonObj = ops.get(cacheName);
        if(jsonObj==null){
            return null;
        }
        String jsonStr = (String) jsonObj;
        return JSON.parseObject(jsonStr, c);
    }

    @Override
    public <T> List<T> getListRedisCache(String cacheName, Class<T> c) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations ops = redisTemplate.opsForValue();
        try {
            if (ops.get(cacheName) == null) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
        String jsonStr = (String) ops.get(cacheName);
        return JSON.parseArray(jsonStr, c);
    }

    @Override
    public <K, V> Map<K, V> getMapRedisByKeyCache(String cacheName, Class<K> key, Class<V> value) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations ops = redisTemplate.opsForValue();
        Map<K, V> jsonObjMap = null;
        try {
            if (ops.get(cacheName) == null) {
                return null;
            }
            String jsonStr = (String) ops.get(cacheName);
            jsonObjMap = JSON.parseObject(jsonStr, new TypeReference<Map<K, V>>(key, value) {
            });
        } catch (Exception e) {
            System.out.println(cacheName + " redis error:" + e.getMessage());
        }
        return jsonObjMap;
    }

    @Override
    public <K, V> Map<K, List<V>> getMapRedisByKeyCacheForValueIsList(String cacheName, Class<K> key, Class<V> value) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations ops = redisTemplate.opsForValue();
        Map<K, List<V>> jsonObjMap = null;
        try {
            if (ops.get(cacheName) == null) {
                return null;
            }
            String jsonStr = (String) ops.get(cacheName);
            jsonObjMap = JSON.parseObject(jsonStr, new TypeReference<Map<K, List<V>>>(key, value) {
            });
        } catch (Exception e) {
            System.out.println(cacheName + " redis error:" + e.getMessage());
        }
        return jsonObjMap;
    }

    @Override
    public void sendToQueue(String queueName, Object msgObj) {
        String jsonString = JSON.toJSONString(msgObj);
        messageProducer.sendMessage(queueName,jsonString);
    }
}
