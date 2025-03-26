package com.interbank.test.controller;

import com.interbank.test.entity.Transaction;
import com.interbank.test.service.TransactionService;
import com.interbank.test.service.dto.TransactionRequestDto;
import com.interbank.test.service.dto.TransactionResponseDto;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

        private final TransactionService transactionService;

        @PostMapping
        public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequestDto transactionRequestDto) {
                Transaction createdTransaction = transactionService.createTransaction(transactionRequestDto);
                return ResponseEntity.ok(createdTransaction);
        }

        @GetMapping("/{transactionExternalId}")
        public ResponseEntity<TransactionResponseDto> getTransactionByExternalId(@PathVariable UUID transactionExternalId) {
                Optional<TransactionResponseDto> transactionResponse = transactionService.getTransactionByExternalId(transactionExternalId);
                return transactionResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }
}
