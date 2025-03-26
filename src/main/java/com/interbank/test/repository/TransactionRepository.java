package com.interbank.test.repository;

import com.interbank.test.entity.Transaction;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
        Optional<Transaction> findByTransactionExternalId(UUID transactionExternalId);
}
