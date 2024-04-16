package com.cooba.listener;

import com.cooba.config.KafkaTopicConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @KafkaListener(id = "myId", topics = KafkaTopicConfig.TEST)
    public void listen(String in) {
        System.out.println(in);
    }
}
