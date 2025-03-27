package com.ibk.challenge.service;

import com.ibk.challenge.model.Transaction;
import com.ibk.challenge.repository.TransactionRepository;
import com.ibk.challenge.repository.TransactionStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TransactionRepository transactionRepository;
    private final TransactionStatusRepository transactionStatusRepository;

    @Autowired
    public KafkaService(KafkaTemplate<String, String> kafkaTemplate, TransactionRepository transactionRepository, TransactionStatusRepository transactionStatusRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.transactionRepository = transactionRepository;
        this.transactionStatusRepository = transactionStatusRepository;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    @KafkaListener(topics = "transactions", groupId = "transacciones-consumer-group")
    public void consumeTransaction(String transactionExternalId) {
        Transaction transaction = transactionRepository.findById(UUID.fromString(transactionExternalId)).orElse(null);
        if (transaction != null) {
            if (transaction.getValue().compareTo(new BigDecimal("1000")) > 0) {
                transaction.setTransactionStatus(transactionStatusRepository.findByName("Rechazado"));
            } else {
                transaction.setTransactionStatus(transactionStatusRepository.findByName("Aprobado"));
            }
            transactionRepository.save(transaction);
        }
    }
}