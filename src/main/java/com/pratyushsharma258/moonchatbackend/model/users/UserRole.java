package com.pratyushsharma258.moonchatbackend.model.users;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role name;

    public UserRole() {
    }

    public UserRole(Role name) {
        this.name = name;
    }
}
