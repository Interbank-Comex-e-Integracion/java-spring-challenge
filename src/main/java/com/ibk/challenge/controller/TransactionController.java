package com.ibk.challenge.controller;

import com.ibk.challenge.dto.TransactionDto;
import com.ibk.challenge.dto.TransactionRequestDto;
import com.ibk.challenge.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
        TransactionDto createdTransaction = transactionService.createTransaction(transactionRequestDto);
        return ResponseEntity.ok(createdTransaction);
    }

    @GetMapping("/{transactionExternalId}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable UUID transactionExternalId) {
        TransactionDto transaction = transactionService.getTransactionDto(transactionExternalId);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}