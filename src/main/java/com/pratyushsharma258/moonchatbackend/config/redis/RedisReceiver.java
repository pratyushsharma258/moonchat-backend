package com.pratyushsharma258.moonchatbackend.config.redis;

import com.pratyushsharma258.moonchatbackend.model.chat.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisReceiver {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void receiveMessage(String message) {
        try {
            ChatMessage chatMessage = objectMapper.readValue(message, ChatMessage.class);
            if (chatMessage.getReceiver() != null) {
                messagingTemplate.convertAndSend("/topic/private/" + chatMessage.getReceiver(), chatMessage);
            } else if (chatMessage.getGroupId() != null) {
                messagingTemplate.convertAndSend("/topic/group/" + chatMessage.getGroupId(), chatMessage);
            } else {
                messagingTemplate.convertAndSend("/topic/public", chatMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}