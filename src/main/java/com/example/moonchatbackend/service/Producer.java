package com.example.moonchatbackend.service;

import com.example.moonchatbackend.model.chat.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);
    private static final String TOPIC = "messages";

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    public boolean updateMessages(ChatMessage message) {
        kafkaTemplate.send(TOPIC, message);
        return true;
    }
}
