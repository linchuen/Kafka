package com.cooba.service;

import com.cooba.config.KafkaTopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class SendService {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void asyncSend() {
        kafkaTemplate.send(KafkaTopicConfig.TEST, "test send async Data");
        log.info("send async Data success");
    }

    public void syncSend() {
        try {
            SendResult<String, String> syncData = kafkaTemplate.send(KafkaTopicConfig.TEST, "test send sync Data").get();
            log.info("send sync Data success");
            ProducerRecord<String, String> producerRecord = syncData.getProducerRecord();
            log.info("topic: {} assign partition: {}", producerRecord.topic(), producerRecord.partition());
            RecordMetadata metadata = syncData.getRecordMetadata();
            log.info("actual partition: {}", metadata.partition());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
