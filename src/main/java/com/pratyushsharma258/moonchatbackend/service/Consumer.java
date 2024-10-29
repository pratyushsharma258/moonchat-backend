package com.pratyushsharma258.moonchatbackend.service;

import com.pratyushsharma258.moonchatbackend.model.chat.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Consumer {

    private static final int BATCH_SIZE = 2;
    private static final int MAX_RETRIES = 10;
    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final ChatMessage[] messageBuffer = new ChatMessage[BATCH_SIZE];
    private final List<ChatMessage> backupBuffer = new ArrayList<>();
    private int head = 0;
    private int tail = 0;
    private int size = 0;

    @Autowired
    private TransactionalService transactionalService;

    @KafkaListener(topics = "messages", groupId = "chat-receiver")
    public void consumeMessage(ChatMessage message) {
        synchronized (this) {
            addToBuffer(message);
            logger.info("Message received to Kafka: {}", message);

            if (size == BATCH_SIZE) {
                saveBatchWithRetry();
                clearBuffer();
            }
        }
    }

    private void addToBuffer(ChatMessage message) {
        messageBuffer[tail] = message;
        tail = (tail + 1) % BATCH_SIZE;
        if (size < BATCH_SIZE) {
            size++;
        } else {
            head = (head + 1) % BATCH_SIZE;
        }
    }

    private void saveBatchWithRetry() {
        List<ChatMessage> messagesToSave = getCurrentBufferMessages();
        int retryCount = 0;

        while (retryCount < MAX_RETRIES) {
            try {
                transactionalService.saveBatchToDatabase(messagesToSave);
                logger.info("Batch of {} messages saved to the database.", messagesToSave.size());
                return;
            } catch (Exception e) {
                retryCount++;
                logger.error("Failed to save batch to database, attempt {}/{}", retryCount, MAX_RETRIES, e);
            }
        }

        backupBuffer.addAll(messagesToSave);
    }

    private List<ChatMessage> getCurrentBufferMessages() {
        List<ChatMessage> currentMessages = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            currentMessages.add(messageBuffer[(head + i) % BATCH_SIZE]);
        }
        return currentMessages;
    }

    private void clearBuffer() {
        head = 0;
        tail = 0;
        size = 0;
    }

    public List<ChatMessage> getUncommittedMessages() {
        return backupBuffer;
    }
}
