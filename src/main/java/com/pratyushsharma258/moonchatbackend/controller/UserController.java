package com.pratyushsharma258.moonchatbackend.controller;

import com.pratyushsharma258.moonchatbackend.model.chat.ChatGroup;
import com.pratyushsharma258.moonchatbackend.model.chat.ChatMessage;
import com.pratyushsharma258.moonchatbackend.model.users.User;
import com.pratyushsharma258.moonchatbackend.security.services.UserDetailsImpl;
import com.pratyushsharma258.moonchatbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public UserDetailsImpl getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @GetMapping("/{userId}")
    public User getUserDetails(@PathVariable Long userId) {
        return userService.getUserDetails(userId);
    }

    @GetMapping("/{userId}/groups")
    public List<ChatGroup> getUserGroups(@PathVariable Long userId) {
        return userService.getUserGroups(userId);
    }

    @GetMapping("/{userId}/messages")
    public List<ChatMessage> getUserMessages(@PathVariable String userId) {
        return userService.getUserMessages(userId);
    }

    @GetMapping("/{userId}/group/{groupId}/messages")
    public List<ChatMessage> getGroupMessages(@PathVariable Long userId, @PathVariable Long groupId) {
        return userService.getGroupMessages(groupId);
    }
}