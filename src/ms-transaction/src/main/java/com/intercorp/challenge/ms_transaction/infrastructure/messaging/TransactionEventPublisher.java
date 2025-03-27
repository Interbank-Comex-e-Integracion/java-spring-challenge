package com.intercorp.challenge.ms_transaction.infrastructure.messaging;

import org.springframework.stereotype.Component;

import com.intercorp.challenge.ms_transaction.domain.Transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@Component
@RequiredArgsConstructor
public class TransactionEventPublisher {

    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public void publishTransactionCreated(Transaction transaction) {
        kafkaTemplate.send("transaction-created", transaction);
    }
}