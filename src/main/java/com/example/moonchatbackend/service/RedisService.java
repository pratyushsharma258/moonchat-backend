package com.example.moonchatbackend.service;

import com.example.moonchatbackend.model.chat.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisService {

    private final ObjectMapper objectMapper = new ObjectMapper();

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

    public void sendMessage(ChatMessage chatMessage) {
        try {
            String message = objectMapper.writeValueAsString(chatMessage);
            redisTemplate.convertAndSend(redisChannel, message);
            System.out.println("Message sent to Redis channel: " + redisChannel + " with content: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}