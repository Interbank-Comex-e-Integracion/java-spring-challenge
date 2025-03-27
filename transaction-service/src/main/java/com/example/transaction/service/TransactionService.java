package com.example.transaction.service;

import com.example.transaction.model.Transaction;
import com.example.transaction.repository.TransactionRepository;
import com.example.transaction.dto.TransactionRequest;
import com.example.transaction.dto.TransactionResponse;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public TransactionService(TransactionRepository repository, KafkaTemplate<String, String> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public TransactionResponse createTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setAccountExternalIdDebit(request.getAccountExternalIdDebit());
        transaction.setAccountExternalIdCredit(request.getAccountExternalIdCredit());
        transaction.setTransferTypeId(request.getTransferTypeId());
        transaction.setValue(request.getValue());
        transaction.setTransactionStatus("PENDING");

        Transaction savedTransaction = repository.save(transaction);
        kafkaTemplate.send("transactions", savedTransaction.getTransactionExternalId().toString());

        return new TransactionResponse(savedTransaction.getTransactionExternalId(), savedTransaction.getTransactionStatus(), savedTransaction.getValue(), savedTransaction.getCreatedAt().toString());
    }

    public Optional<TransactionResponse> getTransaction(UUID id) {
        return repository.findById(id).map(t -> new TransactionResponse(t.getTransactionExternalId(), t.getTransactionStatus(), t.getValue(), t.getCreatedAt().toString()));
    }
    
    public Optional<TransactionResponse> updateTransactionStatus(UUID transactionId, String status) {
        return repository.findById(transactionId).map(transaction -> {
            transaction.setTransactionStatus(status);
            repository.save(transaction);
            return new TransactionResponse(transaction.getTransactionExternalId(), transaction.getTransactionStatus(), transaction.getValue(), transaction.getCreatedAt().toString());
        });
    }
}
