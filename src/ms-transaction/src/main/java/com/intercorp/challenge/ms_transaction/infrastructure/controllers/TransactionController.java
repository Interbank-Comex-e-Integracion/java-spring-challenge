package com.intercorp.challenge.ms_transaction.infrastructure.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.intercorp.challenge.ms_transaction.application.TransactionService;
import com.intercorp.challenge.ms_transaction.domain.Transaction;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/{transactionExternalId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable UUID transactionExternalId) {
        return transactionService.getTransactionByExternalId(transactionExternalId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}