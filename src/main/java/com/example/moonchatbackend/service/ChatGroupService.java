package com.example.moonchatbackend.service;

import com.example.moonchatbackend.model.chat.ChatGroup;
import com.example.moonchatbackend.model.chat.ChatMessage;
import com.example.moonchatbackend.model.users.User;
import com.example.moonchatbackend.repository.ChatGroupRepository;
import com.example.moonchatbackend.repository.ChatMessageRepository;
import com.example.moonchatbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ChatGroupService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public ChatGroup createGroup(String groupName, List<User> users) {
        ChatGroup group = new ChatGroup();
        group.setGroupName(groupName);
        group.setUsers(new HashSet<>(users));
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

    public void deleteGroup(Long groupId) {
        chatGroupRepository.deleteById(groupId);
    }

    public ChatGroup addMembersToGroup(Long groupId, List<Long> userIds) {
        Optional<ChatGroup> groupOpt = chatGroupRepository.findById(groupId);
        if (groupOpt.isPresent()) {
            ChatGroup group = groupOpt.get();
            List<User> usersToAdd = userRepository.findAllById(userIds);
            group.getUsers().addAll(new HashSet<>(usersToAdd));
            return chatGroupRepository.save(group);
        }
        return null;
    }

    public ChatGroup removeMembersFromGroup(Long groupId, List<Long> userIds) {
        Optional<ChatGroup> groupOpt = chatGroupRepository.findById(groupId);
        if (groupOpt.isPresent()) {
            ChatGroup group = groupOpt.get();
            List<User> usersToRemove = userRepository.findAllById(userIds);
            group.getUsers().removeAll(new HashSet<>(usersToRemove));
            return chatGroupRepository.save(group);
        }
        return null;
    }
}