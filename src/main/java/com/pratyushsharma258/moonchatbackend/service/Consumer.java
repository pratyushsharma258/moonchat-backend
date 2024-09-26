package com.pratyushsharma258.moonchatbackend.service;

import com.pratyushsharma258.moonchatbackend.model.chat.ChatMessage;
import com.pratyushsharma258.moonchatbackend.repository.ChatMessageRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Consumer {

    private static final int BATCH_SIZE = 2;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private List<ChatMessage> messageBuffer = new ArrayList<>();

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    @KafkaListener(topics = "messages", groupId = "chat-receiver")
    public void consumeMessage(ChatMessage message) {
        messageBuffer.add(message);
        logger.info("Message received to kafka: {}", message);
        kafkaTemplate.send("uncommitted-messages", message);

        if (messageBuffer.size() >= BATCH_SIZE) {
            saveBatchToDatabase();
            messageBuffer.clear();
        }
    }

    @Transactional
    public void saveBatchToDatabase() {
        chatMessageRepository.saveAll(messageBuffer);
        messageBuffer.forEach(message -> kafkaTemplate.send("remove-uncommitted-messages", message));
        logger.info("Batch of {} messages saved to the database.", messageBuffer.size());
    }
}