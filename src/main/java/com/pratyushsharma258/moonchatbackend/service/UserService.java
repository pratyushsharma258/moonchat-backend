package com.pratyushsharma258.moonchatbackend.service;

import com.pratyushsharma258.moonchatbackend.model.chat.ChatGroup;
import com.pratyushsharma258.moonchatbackend.model.chat.ChatMessage;
import com.pratyushsharma258.moonchatbackend.model.users.User;
import com.pratyushsharma258.moonchatbackend.repository.ChatGroupRepository;
import com.pratyushsharma258.moonchatbackend.repository.ChatMessageRepository;
import com.pratyushsharma258.moonchatbackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatGroupRepository groupRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplate;

    private List<ChatMessage> uncommittedMessages = new ArrayList<>();

    @KafkaListener(topics = "uncommitted-messages", groupId = "chat-receiver")
    public void listenUncommittedMessages(ChatMessage message) {
        uncommittedMessages.add(message);
    }

    public User getUserDetails(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<ChatGroup> getUserGroups(Long userId) {
        return groupRepository.findByUsers_Id(userId);
    }

    public List<ChatMessage> getUserMessages(String username) {
        List<ChatMessage> committedMessages = chatMessageRepository.findByUsername(username);
        log.info("User {} has {} committed messages", username, committedMessages.size());
        List<ChatMessage> userUncommittedMessages = uncommittedMessages.stream()
                .filter(message -> username.equals(message.getSender()) || username.equals(message.getReceiver()))
                .collect(Collectors.toList());
        log.info("User {} has {} uncommitted messages", username, userUncommittedMessages.size());
        committedMessages.addAll(userUncommittedMessages);
        return committedMessages;
    }

    public List<ChatMessage> getGroupMessages(Long groupId) {
        return chatMessageRepository.findByChatGroup_Id(groupId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}