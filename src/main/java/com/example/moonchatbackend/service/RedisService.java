package com.example.moonchatbackend.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private String redisChannel;

    @PostConstruct
    public void checkRedisConnection() {
        try {
            redisTemplate.getConnectionFactory().getConnection().ping();
            System.out.println("Successfully connected to Redis");
        } catch (Exception e) {
            System.err.println("Failed to connect to Redis: " + e.getMessage());
        }
    }

    public void sendMessage(String message) {
        redisTemplate.convertAndSend(redisChannel, message);
        System.out.println("Message sent to Redis channel: " + redisChannel + " with content: " + message);
    }
}