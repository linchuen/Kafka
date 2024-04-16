package com.cooba.service;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SendServiceTest {
    @Autowired
    SendService sendService;

    @Test
    @DisplayName("預設傳送方法")
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
            }else{
                int currentPartition = metadata.partition();
                Assertions.assertNotEquals(lastPartition , currentPartition ,"未設定的情況，partition會輪詢");

                lastPartition = currentPartition;
            }
        }
    }
}