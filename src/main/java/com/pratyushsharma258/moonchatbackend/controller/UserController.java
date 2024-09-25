package com.pratyushsharma258.moonchatbackend.controller;

import com.pratyushsharma258.moonchatbackend.model.chat.ChatGroup;
import com.pratyushsharma258.moonchatbackend.model.chat.ChatMessage;
import com.pratyushsharma258.moonchatbackend.model.users.User;
import com.pratyushsharma258.moonchatbackend.model.users.UserDao;
import com.pratyushsharma258.moonchatbackend.security.services.UserDetailsImpl;
import com.pratyushsharma258.moonchatbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    private UserDetailsImpl getAuthenticatedUser(String authorizationHeader) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    @GetMapping("/users")
    public List<UserDao> getAllUsers(@RequestHeader("Authorization") String authorizationHeader) {
        UserDetailsImpl authenticatedUser = getAuthenticatedUser(authorizationHeader);
        List<User> users = userService.getAllUsers();
        return UserDao.userDaosFromUsers(users);
    }

    @GetMapping("/me")
    public UserDetailsImpl getAuthenticatedUserDetails(@RequestHeader("Authorization") String authorizationHeader) {
        return getAuthenticatedUser(authorizationHeader);
    }

    @GetMapping("/details")
    public User getUserDetails(@RequestHeader("Authorization") String authorizationHeader) {
        UserDetailsImpl authenticatedUser = getAuthenticatedUser(authorizationHeader);
        return userService.getUserDetails(authenticatedUser.getId());
    }

    @GetMapping("/groups")
    public List<ChatGroup> getUserGroups(@RequestHeader("Authorization") String authorizationHeader) {
        UserDetailsImpl authenticatedUser = getAuthenticatedUser(authorizationHeader);
        return userService.getUserGroups(authenticatedUser.getId());
    }

    @GetMapping("/messages")
    public List<ChatMessage> getUserMessages(@RequestHeader("Authorization") String authorizationHeader) {
        UserDetailsImpl authenticatedUser = getAuthenticatedUser(authorizationHeader);
        log.info("Getting messages for user {}", authenticatedUser.getId());
        return userService.getUserMessages(authenticatedUser.getUsername().toString());
    }

    @GetMapping("/group/{groupId}/messages")
    public List<ChatMessage> getGroupMessages(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long groupId) {
        UserDetailsImpl authenticatedUser = getAuthenticatedUser(authorizationHeader);
        return userService.getGroupMessages(groupId);
    }
}