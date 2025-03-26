package com.interbank.test.service;

import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageService {

        private final List<String> messages = new ArrayList<>();

        @KafkaListener(topics = "transaction-status", groupId = "anti-fraud-group")
        public void listen(ConsumerRecord<String, String> record) {
                messages.add("Key: " + record.key() + ", Value: " + record.value());
        }

        public List<String> getMessages() {
                return new ArrayList<>(messages);
        }
}
