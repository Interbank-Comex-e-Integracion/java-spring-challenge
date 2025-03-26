package com.challenge.demo.controller;

import com.challenge.demo.dto.CreateTransactionRequest;
import com.challenge.demo.dto.TransactionResponse;
import com.challenge.demo.service.TransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody CreateTransactionRequest request) {
        logger.info("Received create transaction request: {}", request);
        TransactionResponse response = transactionService.createTransaction(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{transactionExternalId}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable String transactionExternalId) {
        logger.info("Received get transaction request for ID: {}", transactionExternalId);
        TransactionResponse response = transactionService.getTransaction(transactionExternalId);
        return ResponseEntity.ok(response);
    }
}