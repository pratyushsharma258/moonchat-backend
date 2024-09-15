package com.example.moonchatbackend.model.chat;

import com.example.moonchatbackend.model.users.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class ChatGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Group name is required.")
    private String groupName;

    @ManyToMany
    @JoinTable(
            name = "group_users",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    @OneToMany(mappedBy = "chatGroup")
    private Set<ChatMessage> messages;

}

