package com.intercorp.challenge.ms_anti_fraud.infrastructure.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.intercorp.challenge.ms_anti_fraud.domain.model.Transaction;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionEventPublisher {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public void publishTransactionUpdated(Transaction transaction) {
        kafkaTemplate.send("transaction-updated", transaction);
    }
}