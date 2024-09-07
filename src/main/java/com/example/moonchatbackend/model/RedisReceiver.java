package com.example.moonchatbackend.model;

import org.springframework.stereotype.Component;

@Component
public class RedisReceiver {


    public void receiveMessage(String message) {
        System.out.println("Got Message: " + message);

        System.out.println("Message received by RedisReceiver instance: " + this);
    }
}