package com.example.moonchatbackend.controller;

import com.example.moonchatbackend.model.chat.ChatMessage;
import com.example.moonchatbackend.service.Producer;
import com.example.moonchatbackend.service.RedisService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private Producer producer;

    @MessageMapping("/chat/send")
    public void sendMessage(@Valid @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String sessionId = headerAccessor.getSessionId();
        chatMessage.setSessionId(sessionId);
        redisService.sendMessage(chatMessage);
        producer.updateMessages(chatMessage);
    }

    @MessageMapping("/chat/adduser")
    public void addUser(@Valid @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        redisService.sendMessage(chatMessage);
        producer.updateMessages(chatMessage);
    }

    @MessageMapping("/chat/private")
    public void sendPrivateMessage(@Valid @Payload ChatMessage chatMessage) {
        redisService.sendMessage(chatMessage);
        producer.updateMessages(chatMessage);
    }

    @MessageMapping("/chat/group")
    public void sendGroupMessage(@Valid @Payload ChatMessage chatMessage) {
        redisService.sendMessage(chatMessage);
        producer.updateMessages(chatMessage);
    }
}