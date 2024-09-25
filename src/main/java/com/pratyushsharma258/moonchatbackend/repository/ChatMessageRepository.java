package com.pratyushsharma258.moonchatbackend.repository;

import com.pratyushsharma258.moonchatbackend.model.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT cm FROM ChatMessage cm WHERE cm.sender = :username OR cm.receiver = :username")
    List<ChatMessage> findByUsername(@Param("username") String username);

    List<ChatMessage> findByChatGroup_Id(Long groupId);
}