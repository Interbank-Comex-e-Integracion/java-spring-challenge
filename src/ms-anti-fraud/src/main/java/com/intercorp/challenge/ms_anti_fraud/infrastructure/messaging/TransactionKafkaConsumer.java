package com.intercorp.challenge.ms_anti_fraud.infrastructure.messaging;

import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;
import com.intercorp.challenge.ms_anti_fraud.application.FraudService;
import com.intercorp.challenge.ms_anti_fraud.domain.model.Transaction;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionKafkaConsumer {

    private final FraudService fraudService;

    @KafkaListener(topics = "transaction-created", groupId = "anti-fraud-group")
    public void consumeTransactionCreated(Transaction transaction) {
        fraudService.processTransaction(transaction);
    }
}