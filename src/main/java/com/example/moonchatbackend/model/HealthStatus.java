package com.example.moonchatbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class HealthStatus {
    private String status;
    private LocalDateTime timestamp;
}