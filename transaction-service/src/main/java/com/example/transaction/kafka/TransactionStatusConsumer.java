package com.example.transaction.kafka;

import com.example.transaction.service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class TransactionStatusConsumer {
    private final TransactionService transactionService;

    public TransactionStatusConsumer(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @KafkaListener(topics = "antifraud", groupId = "transaction-update")
    public void listen(String message) {
        String[] parts = message.split(",");
        if (parts.length == 2) {
            UUID transactionId = UUID.fromString(parts[0]);
            String status = parts[1];
            transactionService.updateTransactionStatus(transactionId, status);
        }
    }
}
