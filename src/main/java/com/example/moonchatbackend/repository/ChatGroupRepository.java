package com.example.moonchatbackend.repository;

import com.example.moonchatbackend.model.chat.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatGroupRepository extends JpaRepository<ChatGroup, Long> {
}

