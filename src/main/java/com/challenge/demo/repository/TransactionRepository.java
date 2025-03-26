package com.challenge.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.challenge.demo.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionExternalId(String transactionExternalId);
}