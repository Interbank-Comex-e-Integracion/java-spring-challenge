package com.intercorp.challenge.ms_anti_fraud.application;

import org.springframework.stereotype.Service;

import com.intercorp.challenge.ms_anti_fraud.domain.FraudValidationService;
import com.intercorp.challenge.ms_anti_fraud.domain.model.Transaction;
import com.intercorp.challenge.ms_anti_fraud.domain.model.TransactionStatus;
import com.intercorp.challenge.ms_anti_fraud.infrastructure.messaging.TransactionEventPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FraudService {

    private final FraudValidationService fraudValidationService;
    private final TransactionEventPublisher transactionEventPublisher;

    public void processTransaction(Transaction transaction) {
        TransactionStatus status = fraudValidationService.validateTransaction(transaction);
        transaction.setTransactionStatus(status);
        transactionEventPublisher.publishTransactionUpdated(transaction);
    }
}