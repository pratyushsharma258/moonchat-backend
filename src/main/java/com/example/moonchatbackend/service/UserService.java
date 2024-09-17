package com.example.moonchatbackend.service;

import com.example.moonchatbackend.model.chat.ChatGroup;
import com.example.moonchatbackend.model.chat.ChatMessage;
import com.example.moonchatbackend.model.users.User;
import com.example.moonchatbackend.repository.ChatGroupRepository;
import com.example.moonchatbackend.repository.ChatMessageRepository;
import com.example.moonchatbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatGroupRepository groupRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public User getUserDetails(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<ChatGroup> getUserGroups(Long userId) {
        return groupRepository.findByUsers_Id(userId);
    }

    public List<ChatMessage> getUserMessages(String userId) {
        return chatMessageRepository.findBySenderOrReceiver(userId, userId);
    }

    public List<ChatMessage> getGroupMessages(Long groupId) {
        return chatMessageRepository.findByChatGroup_Id(groupId);
    }
}