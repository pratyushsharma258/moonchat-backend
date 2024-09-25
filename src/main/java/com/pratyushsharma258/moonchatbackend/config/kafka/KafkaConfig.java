package com.pratyushsharma258.moonchatbackend.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic messagesTopic() {
        return TopicBuilder
                .name("messages")
                .build();
    }

    @Bean
    public NewTopic uncommittedMessagesTopic() {
        return TopicBuilder
                .name("uncommitted-messages")
                .build();
    }

    @Bean
    public NewTopic groupMessagesTopic() {
        return TopicBuilder
                .name("remove-uncommitted-messages")
                .build();
    }

}