package com.intercorp.challenge.ms_transaction.domain;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findByTransactionExternalId(UUID transactionExternalId);
    Transaction update(Transaction transaction);
}