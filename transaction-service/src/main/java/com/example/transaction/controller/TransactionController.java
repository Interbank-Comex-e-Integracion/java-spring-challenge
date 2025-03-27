package com.example.transaction.controller;

import com.example.transaction.dto.TransactionRequest;
import com.example.transaction.dto.TransactionResponse;
import com.example.transaction.service.TransactionService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
        return service.createTransaction(request);
    }

    @GetMapping("/{id}")
    public Optional<TransactionResponse> getTransaction(@PathVariable UUID id) {
        return service.getTransaction(id);
    }
    
    @PutMapping("/{id}/status")
    public Optional<TransactionResponse> updateTransactionStatus(@PathVariable UUID id, @RequestBody Map<String, String> request) {
        String status = request.get("status");
        return service.updateTransactionStatus(id, status);
    }
}
