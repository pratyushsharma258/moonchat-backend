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
    private Consumer consumer;

    public User getUserDetails(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<ChatGroup> getUserGroups(Long userId) {
        return groupRepository.findByUsers_Id(userId);
    }

    public List<ChatMessage> getUserMessages(String username) {
        List<ChatMessage> committedMessages = chatMessageRepository.findByUsername(username);
        log.info("User {} has {} committed messages", username, committedMessages.size());

        List<ChatMessage> userUncommittedMessages = consumer.getUncommittedMessages().stream()
                .filter(message -> username.equals(message.getSender()) || username.equals(message.getReceiver()))
                .collect(Collectors.toList());

        log.info("User {} has {} uncommitted messages", username, userUncommittedMessages.size());

        committedMessages.addAll(userUncommittedMessages);

        return committedMessages.stream()
                .collect(Collectors.toMap(ChatMessage::getSentAt, message -> message, (existing, replacement) -> existing))
                .values().stream()
                .sorted((m1, m2) -> m2.getSentAt().compareTo(m1.getSentAt()))
                .collect(Collectors.toList());
    }

    public List<ChatMessage> getGroupMessages(Long groupId) {
        return chatMessageRepository.findByChatGroup_Id(groupId).stream()
                .sorted((m1, m2) -> m2.getSentAt().compareTo(m1.getSentAt()))
                .collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
