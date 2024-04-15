package com.cooba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @PostMapping("/test")
    public String test(){
        kafkaTemplate.send("json","123");
        return "";
    }
}
