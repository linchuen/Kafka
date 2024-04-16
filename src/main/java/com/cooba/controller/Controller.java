package com.cooba.controller;

import com.cooba.service.SendService;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private SendService sendService;

    @PostMapping("/async")
    public ResponseEntity<Void> asyncSend(){
        sendService.asyncSend();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sync")
    public ResponseEntity<Integer> syncSend(){
        RecordMetadata metadata = sendService.syncSend("message");
        return ResponseEntity.ok(metadata.partition());
    }
}
