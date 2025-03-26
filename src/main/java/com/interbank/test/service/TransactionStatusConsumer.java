package com.interbank.test.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionStatusConsumer {

        @KafkaListener(topics = "transaction-status", groupId = "test-group")
        public void listen(ConsumerRecord<String, String> record) {
                System.out.println("Received Message: " + record.value());
        }
}
