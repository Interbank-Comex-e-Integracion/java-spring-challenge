package com.intercorp.challenge.ms_transaction.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import com.intercorp.challenge.ms_transaction.domain.Transaction;
import com.intercorp.challenge.ms_transaction.domain.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Transaction save(Transaction transaction) {
        log.info("Transaction to be saved: {}", transaction);
        TransactionEntity transactionEntity = transactionMapper.toEntity(transaction);
        log.info("Transaction Entity: {}", transactionEntity);
        TransactionEntity savedEntity = transactionJpaRepository.save(transactionEntity);
        log.info("Transaction Entity saved: {}", transactionEntity);
        return transactionMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Transaction> findByTransactionExternalId(UUID transactionExternalId) {
        return transactionJpaRepository.findByTransactionExternalId(transactionExternalId)
                .map(transactionMapper::toDomain);
    }

    @Override
    public Transaction update(Transaction transaction) {
        TransactionEntity transactionEntity = transactionMapper.toEntity(transaction);
        TransactionEntity updatedEntity = transactionJpaRepository.save(transactionEntity);
        return transactionMapper.toDomain(updatedEntity);
    }
}
