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

    public RecordMetadata syncSend(String message) {
        try {
            SendResult<String, String> syncData = kafkaTemplate.send(KafkaTopicConfig.TEST, message).get();
            log.info("send sync Data success");
            ProducerRecord<String, String> producerRecord = syncData.getProducerRecord();
            log.info("topic: {} assign partition: {}", producerRecord.topic(), producerRecord.partition());
            RecordMetadata metadata = syncData.getRecordMetadata();
            log.info("actual partition: {}", metadata.partition());
            return metadata;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public RecordMetadata syncSend(int partition, String message) {
        try {
            SendResult<String, String> syncData = kafkaTemplate.send(KafkaTopicConfig.TEST, partition, "", message).get();
            log.info("send sync Data with partition");
            RecordMetadata metadata = syncData.getRecordMetadata();
            log.info("actual partition: {}", metadata.partition());
            return metadata;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public RecordMetadata syncSend(String key, String message) {
        try {
            SendResult<String, String> syncData = kafkaTemplate.send(KafkaTopicConfig.TEST, key, message).get();
            log.info("send sync Data with key");
            RecordMetadata metadata = syncData.getRecordMetadata();
            log.info("actual partition: {}", metadata.partition());
            return metadata;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
