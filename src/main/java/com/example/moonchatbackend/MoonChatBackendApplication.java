package com.example.moonchatbackend;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoonChatBackendApplication {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(MoonChatBackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MoonChatBackendApplication.class, args);
    }

}
