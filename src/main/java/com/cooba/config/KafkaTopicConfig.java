package com.cooba.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String TEST = "test";

    @Bean
    public NewTopic KafkaTopic() {
        return TopicBuilder
                .name(TEST)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
