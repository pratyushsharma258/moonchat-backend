package com.pratyushsharma258.moonchatbackend.controller;

import com.pratyushsharma258.moonchatbackend.model.chat.ChatGroup;
import com.pratyushsharma258.moonchatbackend.model.users.User;
import com.pratyushsharma258.moonchatbackend.repository.ChatGroupRepository;
import com.pratyushsharma258.moonchatbackend.repository.UserRepository;
import com.pratyushsharma258.moonchatbackend.service.ChatGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/groups")
public class ChatGroupController {
    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/create")
    public ResponseEntity<ChatGroup> createGroup(@RequestParam String groupName, @RequestBody List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        ChatGroup group = chatGroupService.createGroup(groupName, users);

        Map<String, Object> notification = new HashMap<>();
        notification.put("event", "GROUP_CREATED");
        notification.put("groupId", group.getId());
        notification.put("groupName", groupName);
        notification.put("userIds", userIds);

        for (User user : users) {
            messagingTemplate.convertAndSend("/topic/groups/" + user.getId(), notification);
        }

        return ResponseEntity.ok(group);
    }

    @DeleteMapping("/delete/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) {
        ChatGroup group = chatGroupRepository.getChatGroupById(groupId);
        Set<User> users = group.getUsers();

        chatGroupService.deleteGroup(groupId);

        Map<String, Object> notification = new HashMap<>();
        notification.put("event", "GROUP_DELETED");
        notification.put("groupId", groupId);

        for (User user : users) {
            messagingTemplate.convertAndSend("/topic/groups/" + user.getId(), notification);
        }

        return ResponseEntity.ok("Group deleted successfully");
    }

    @PostMapping("/{groupId}/add-members")
    public ResponseEntity<ChatGroup> addMembers(@PathVariable Long groupId, @RequestBody List<Long> userIds) {
        ChatGroup updatedGroup = chatGroupService.addMembersToGroup(groupId, userIds);

        Map<String, Object> notification = new HashMap<>();
        notification.put("event", "MEMBERS_ADDED");
        notification.put("groupId", groupId);
        notification.put("userIds", userIds);

        Set<User> allUsers = updatedGroup.getUsers();

        for (User user : allUsers) {
            messagingTemplate.convertAndSend("/topic/groups/" + user.getId(), notification);
        }

        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{groupId}/remove-members")
    public ResponseEntity<ChatGroup> removeMembers(@PathVariable Long groupId, @RequestBody List<Long> userIds) {
        List<User> usersToRemove = userRepository.findAllById(userIds);
        ChatGroup updatedGroup = chatGroupService.removeMembersFromGroup(groupId, userIds);

        Map<String, Object> notification = new HashMap<>();
        notification.put("event", "MEMBERS_REMOVED");
        notification.put("groupId", groupId);
        notification.put("userIds", userIds);

        Set<User> remainingUsers = updatedGroup.getUsers();

        for (User user : remainingUsers) {
            messagingTemplate.convertAndSend("/topic/groups/" + user.getId(), notification);
        }

        for (User user : usersToRemove) {
            messagingTemplate.convertAndSend("/topic/groups/" + user.getId(), notification);
        }

        return ResponseEntity.ok(updatedGroup);
    }
}