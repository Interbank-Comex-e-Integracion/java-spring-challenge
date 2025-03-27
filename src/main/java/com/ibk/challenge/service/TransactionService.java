package com.ibk.challenge.service;

import com.ibk.challenge.dto.TransactionDto;
import com.ibk.challenge.dto.TransactionRequestDto;
import com.ibk.challenge.dto.TransactionStatusDto;
import com.ibk.challenge.dto.TransactionTypeDto;
import com.ibk.challenge.model.Transaction;
import com.ibk.challenge.model.TransactionStatus;
import com.ibk.challenge.model.TransactionType;
import com.ibk.challenge.repository.TransactionRepository;
import com.ibk.challenge.repository.TransactionStatusRepository;
import com.ibk.challenge.repository.TransactionTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionStatusRepository transactionStatusRepository;
    private final KafkaService kafkaService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, TransactionTypeRepository transactionTypeRepository, TransactionStatusRepository transactionStatusRepository, KafkaService kafkaService) {
        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionStatusRepository = transactionStatusRepository;
        this.kafkaService = kafkaService;
    }

    public TransactionDto createTransaction(TransactionRequestDto transactionRequestDto) {
        logger.info("Creating transaction with request: {}", transactionRequestDto);

        TransactionType type = transactionTypeRepository.findByName("Transfer");
        TransactionStatus status = transactionStatusRepository.findByName("Pendiente");

        Transaction transaction = new Transaction();
        transaction.setTransactionExternalId(UUID.randomUUID());
        transaction.setAccountExternalIdDebit(transactionRequestDto.accountExternalIdDebit());
        transaction.setAccountExternalIdCredit(transactionRequestDto.accountExternalIdCredit());
        transaction.setTransactionType(type);
        transaction.setTransactionStatus(status);
        transaction.setValue(transactionRequestDto.value());
        transaction.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));

        logger.info("Transaction entity to save: {}", transaction);

        Transaction savedTransaction = transactionRepository.save(transaction);
        kafkaService.sendMessage("transactions", savedTransaction.getTransactionExternalId().toString());
        return mapToDto(savedTransaction);
    }

    public TransactionDto getTransactionDto(UUID transactionExternalId) {
        Transaction transaction = transactionRepository.findById(transactionExternalId).orElse(null);
        return transaction != null ? mapToDto(transaction) : null;
    }

    private TransactionDto mapToDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getTransactionExternalId(),
                transaction.getAccountExternalIdDebit(),
                transaction.getAccountExternalIdCredit(),
                new TransactionTypeDto(transaction.getTransactionType().getId(), transaction.getTransactionType().getName()),
                new TransactionStatusDto(transaction.getTransactionStatus().getId(), transaction.getTransactionStatus().getName()),
                transaction.getValue(),
                transaction.getCreatedAt()
        );
    }

    public void updateTransactionStatus(UUID transactionExternalId, String statusName) {
        Transaction transaction = transactionRepository.findById(transactionExternalId).orElse(null);
        TransactionStatus status = transactionStatusRepository.findByName(statusName);
        if (transaction != null) {
            transaction.setTransactionStatus(status);
            transactionRepository.save(transaction);
        }
    }
}