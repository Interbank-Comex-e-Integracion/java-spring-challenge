package com.intercorp.challenge.ms_anti_fraud.domain;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.intercorp.challenge.ms_anti_fraud.domain.model.Transaction;
import com.intercorp.challenge.ms_anti_fraud.domain.model.TransactionStatus;

@Service
public class FraudValidationService {

    public TransactionStatus validateTransaction(Transaction transaction) {
        if (transaction.getValue().compareTo(new BigDecimal("1000")) > 0) {
            return TransactionStatus.REJECTED;
        }
        return TransactionStatus.APPROVED;
    }
}