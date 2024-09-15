package com.example.moonchatbackend.service;

import com.example.moonchatbackend.model.chat.ChatGroup;
import com.example.moonchatbackend.model.chat.ChatMessage;
import com.example.moonchatbackend.model.users.User;
import com.example.moonchatbackend.repository.ChatGroupRepository;
import com.example.moonchatbackend.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ChatGroupService {

    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatGroup createGroup(String groupName, Set<User> users) {
        ChatGroup group = new ChatGroup();
        group.setGroupName(groupName);
        group.setUsers(users);
        return chatGroupRepository.save(group);
    }

    public ChatMessage sendMessageToGroup(Long groupId, String message, String sender) {
        Optional<ChatGroup> group = chatGroupRepository.findById(groupId);
        if (group.isPresent()) {
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setContent(message);
            chatMessage.setSender(sender);
            chatMessage.setChatGroup(group.get());
            return chatMessageRepository.save(chatMessage);
        }
        return null;
    }
}

