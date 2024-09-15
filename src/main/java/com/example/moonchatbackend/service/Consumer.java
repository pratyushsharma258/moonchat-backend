package com.example.moonchatbackend.service;

import com.example.moonchatbackend.model.chat.ChatMessage;
import com.example.moonchatbackend.repository.ChatMessageRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Consumer {

    private static final int BATCH_SIZE = 10;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private List<ChatMessage> messageBuffer = new ArrayList<>();

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @KafkaListener(topics = "messages", groupId = "chat-receiver")
    public void consumeMessage(ChatMessage message) {
        messageBuffer.add(message);

        if (messageBuffer.size() >= BATCH_SIZE) {
            saveBatchToDatabase();
            messageBuffer.clear();
        }
    }

    @Transactional
    public void saveBatchToDatabase() {
        chatMessageRepository.saveAll(messageBuffer);
        logger.info("Batch of {} messages saved to the database.", messageBuffer.size());
    }
}