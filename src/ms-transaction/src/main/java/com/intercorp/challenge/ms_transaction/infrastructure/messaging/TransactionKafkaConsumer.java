package com.intercorp.challenge.ms_transaction.infrastructure.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.intercorp.challenge.ms_transaction.application.TransactionService;
import com.intercorp.challenge.ms_transaction.domain.Transaction;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionKafkaConsumer {

    private final TransactionService transactionService;

    @KafkaListener(topics = "transaction-updated", groupId = "transaction-group")
    public void consumeTransactionUpdated(Transaction transaction) {
        transactionService.updateTransactionStatus(transaction.getTransactionExternalId(), transaction.getTransactionStatus());
    }
}