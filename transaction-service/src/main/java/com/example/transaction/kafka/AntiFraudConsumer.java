package com.example.transaction.kafka;

import com.example.transaction.model.Transaction;
import com.example.transaction.repository.TransactionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class AntiFraudConsumer {
    private final TransactionRepository repository;
    private final KafkaProducer kafkaProducer;

    public AntiFraudConsumer(TransactionRepository repository, KafkaProducer kafkaProducer) {
        this.repository = repository;
        this.kafkaProducer = kafkaProducer;
    }

    @KafkaListener(topics = "transactions", groupId = "fraud-check")
    public void listen(String transactionId) {
        repository.findById(UUID.fromString(transactionId)).ifPresent(transaction -> {
            String status = transaction.getValue() > 1000 ? "REJECTED" : "APPROVED";
            transaction.setTransactionStatus(status);
            repository.save(transaction);
            kafkaProducer.sendTransactionStatus(transaction.getTransactionExternalId(), status);
        });
    }
}
