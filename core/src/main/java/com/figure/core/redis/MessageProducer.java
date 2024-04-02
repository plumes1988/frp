package com.figure.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public MessageProducer(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessage(String queueName, String message) {
        redisTemplate.convertAndSend(queueName, message);
    }
}