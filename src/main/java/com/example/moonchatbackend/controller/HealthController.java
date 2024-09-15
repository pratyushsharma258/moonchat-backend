package com.example.moonchatbackend.controller;

import com.example.moonchatbackend.model.HealthStatus;
import com.example.moonchatbackend.model.chat.ChatMessage;
import com.example.moonchatbackend.service.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HealthController {

    private final Logger log = LoggerFactory.getLogger(HealthController.class);

    @Autowired
    private Producer producer;

    @GetMapping("/health")
    public ResponseEntity<?> checkHealth() {
        return ResponseEntity.ok().body(new HealthStatus("UP", LocalDateTime.now()));
    }

    @GetMapping("/kafka/test")
    public ResponseEntity<?> test(@RequestBody ChatMessage message) {
        try {
            producer.updateMessages(message);
            log.info("Message sent {}", message);
            return new ResponseEntity<>("Message sent", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error sending message", e);
            return new ResponseEntity<>("Error sending message", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

