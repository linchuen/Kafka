package com.cooba.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @KafkaListener(id = "myId", topics = "json")
    public void listen(String in) {
        System.out.println(in);
    }
}
