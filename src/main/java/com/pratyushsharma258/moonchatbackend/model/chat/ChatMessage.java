package com.pratyushsharma258.moonchatbackend.model.chat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @NotNull(message = "Message sent is empty.")
    private String content;

    @NotNull(message = "Sender is required.")
    private String sender;

    private String receiver;

    @Column(name = "group_id", insertable = false, updatable = false)
    private Long groupId;

    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private ChatGroup chatGroup;
}