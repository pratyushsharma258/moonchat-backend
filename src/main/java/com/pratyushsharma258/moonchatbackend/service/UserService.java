package com.pratyushsharma258.moonchatbackend.service;

import com.pratyushsharma258.moonchatbackend.model.chat.ChatGroup;
import com.pratyushsharma258.moonchatbackend.model.chat.ChatMessage;
import com.pratyushsharma258.moonchatbackend.model.users.User;
import com.pratyushsharma258.moonchatbackend.repository.ChatGroupRepository;
import com.pratyushsharma258.moonchatbackend.repository.ChatMessageRepository;
import com.pratyushsharma258.moonchatbackend.repository.UserRepository;
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