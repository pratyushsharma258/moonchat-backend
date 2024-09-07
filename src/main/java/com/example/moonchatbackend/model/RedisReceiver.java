package com.example.moonchatbackend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisReceiver {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void receiveMessage(String message) {
        System.out.println("Got Message: " + message);
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}