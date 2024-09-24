package com.pratyushsharma258.moonchatbackend.repository;

import com.pratyushsharma258.moonchatbackend.model.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findBySenderOrReceiver(String sender, String receiver);

    List<ChatMessage> findByChatGroup_Id(Long groupId);
}