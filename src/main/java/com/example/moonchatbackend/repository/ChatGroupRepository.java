package com.example.moonchatbackend.repository;

import com.example.moonchatbackend.model.chat.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatGroupRepository extends JpaRepository<ChatGroup, Long> {
    ChatGroup getChatGroupById(Long groupId);

    List<ChatGroup> findByUsers_Id(Long userId);
}