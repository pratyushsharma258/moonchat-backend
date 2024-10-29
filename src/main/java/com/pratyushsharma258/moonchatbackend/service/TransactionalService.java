package com.pratyushsharma258.moonchatbackend.service;

import com.pratyushsharma258.moonchatbackend.model.chat.ChatMessage;
import com.pratyushsharma258.moonchatbackend.repository.ChatMessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionalService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    @Transactional
    public void saveBatchToDatabase(List<ChatMessage> messagesToSave) {
        chatMessageRepository.saveAll(messagesToSave);
        messagesToSave.forEach(message -> kafkaTemplate.send("remove-uncommitted-messages", message));
    }
}
