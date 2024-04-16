package com.cooba.service;

import com.cooba.config.KafkaTopicConfig;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class SendServiceTest {
    @Autowired
    SendService sendService;
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Test
    @DisplayName("預設分區傳輸情境")
    public void syncSend() {
        //DefaultPartitioner:
        // If no partition or key is present choose the sticky partition that changes when the batch is full.
        //RoundRobinPartitioner
        //UniformStickyPartitioner

        Integer lastPartition = null;
        for (int i = 0; i < 10; i++) {
            String message = Instancio.create(String.class);
            RecordMetadata metadata = sendService.syncSend(message);

            if (lastPartition == null) {
                lastPartition = metadata.partition();
            } else {
                int currentPartition = metadata.partition();
                Assertions.assertNotEquals(lastPartition, currentPartition, "未設定的情況，partition會輪詢");

                lastPartition = currentPartition;
            }
        }
    }

    @Test
    @DisplayName("指定分區傳輸情境")
    public void specifyPartition() {
        int partition = 0;
        for (int i = 0; i < 10; i++) {
            String message = Instancio.create(String.class);
            RecordMetadata metadata = sendService.syncSend(partition, message);

            Assertions.assertEquals(partition, metadata.partition(), "指定分區的情況，partition會一致");
        }
    }

    @Test
    @DisplayName("指定分區KEY傳輸情境")
    public void specifyKey() {
        String key = "SpecifyKey";
        Integer lastPartition = null;
        for (int i = 0; i < 10; i++) {
            String message = Instancio.create(String.class);
            RecordMetadata metadata = sendService.syncSend(key, message);

            if (lastPartition == null) {
                lastPartition = metadata.partition();
            } else {
                Assertions.assertEquals(lastPartition, metadata.partition(), "指定分區Key的情況，partition會一致");
            }
        }
    }
}