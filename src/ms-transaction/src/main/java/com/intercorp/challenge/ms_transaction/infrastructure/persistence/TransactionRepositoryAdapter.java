package com.intercorp.challenge.ms_transaction.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import com.intercorp.challenge.ms_transaction.domain.Transaction;
import com.intercorp.challenge.ms_transaction.domain.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity transactionEntity = transactionMapper.toEntity(transaction);
        TransactionEntity savedEntity = transactionJpaRepository.save(transactionEntity);
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
