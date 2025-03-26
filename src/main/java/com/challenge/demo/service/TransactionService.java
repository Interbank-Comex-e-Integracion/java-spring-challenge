package com.challenge.demo.service;

import com.challenge.demo.dto.CreateTransactionRequest;
import com.challenge.demo.dto.TransactionResponse;
import com.challenge.demo.event.TransactionCreatedEvent;
import com.challenge.demo.event.TransactionStatusUpdatedEvent;
import com.challenge.demo.exception.TransactionNotFoundException;
import com.challenge.demo.model.Transaction;
import com.challenge.demo.model.TransactionStatus;
import com.challenge.demo.model.TransactionType;
import com.challenge.demo.repository.TransactionRepository;
import com.challenge.demo.repository.TransactionStatusRepository;
import com.challenge.demo.repository.TransactionTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class TransactionService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionStatusRepository transactionStatusRepository;
    private final KafkaService kafkaService;

    public TransactionService(
            TransactionRepository transactionRepository,
            TransactionTypeRepository transactionTypeRepository,
            TransactionStatusRepository transactionStatusRepository,
            KafkaService kafkaService) {
        this.transactionRepository = transactionRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionStatusRepository = transactionStatusRepository;
        this.kafkaService = kafkaService;
    }

    @Transactional
    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        logger.info("Creating transaction: {}", request);

        TransactionType transactionType = transactionTypeRepository.findById(request.getTranferTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid transaction type ID: " + request
                        .getTranferTypeId()));

        TransactionStatus pendingStatus = transactionStatusRepository.findById(TransactionStatus.PENDING)
                .orElseThrow(() -> new IllegalArgumentException("Pending status not found"));

        Transaction transaction = Transaction.builder()
                .transactionExternalId(UUID.randomUUID().toString())
                .accountExternalIdDebit(request.getAccountExternalIdDebit())
                .accountExternalIdCredit(request.getAccountExternalIdCredit())
                .transactionType(transactionType)
                .transactionStatus(pendingStatus)
                .value(request.getValue())
                .createdAt(Instant.now())
                .build();

        transaction = transactionRepository.save(transaction);
        logger.info("Transaction created with ID: {}", transaction.getTransactionExternalId());

        TransactionCreatedEvent event = TransactionCreatedEvent.builder()
                .transactionExternalId(transaction.getTransactionExternalId())
                .accountExternalIdDebit(transaction.getAccountExternalIdDebit())
                .accountExternalIdCredit(transaction.getAccountExternalIdCredit())
                .transactionTypeId(transaction.getTransactionType().getId())
                .transactionStatusId(transaction.getTransactionStatus().getId())
                .value(transaction.getValue())
                .createdAt(transaction.getCreatedAt())
                .build();

        kafkaService.sendTransactionCreatedEvent(event);

        return mapToResponse(transaction);
    }

    @Transactional(readOnly = true)
    public TransactionResponse getTransaction(String transactionExternalId) {
        logger.info("Getting transaction with external ID: {}", transactionExternalId);

        Transaction transaction = transactionRepository.findByTransactionExternalId(transactionExternalId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionExternalId));

        return mapToResponse(transaction);
    }

    @Transactional
    @KafkaListener(topics = "${kafka.topic.transaction-status-updated}", groupId = "${spring.kafka.consumer.group-id}")
    public void updateTransactionStatus(TransactionStatusUpdatedEvent event) {
        logger.info("Updating transaction status: {}", event);

        Transaction transaction = transactionRepository.findByTransactionExternalId(event.getTransactionExternalId())
                .orElseThrow(() -> new TransactionNotFoundException(event.getTransactionExternalId()));

        TransactionStatus newStatus = transactionStatusRepository.findById(event.getTransactionStatusId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid status ID: " + event
                        .getTransactionStatusId()));

        transaction.setTransactionStatus(newStatus);
        transactionRepository.save(transaction);

        logger.info("Transaction status updated to {} for transaction ID: {}",
                newStatus.getName(), event.getTransactionExternalId());
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .transactionExternalId(transaction.getTransactionExternalId())
                .transactionType(
                        TransactionResponse.TransactionTypeDto.builder()
                                .name(transaction.getTransactionType().getName())
                                .build()
                )
                .transactionStatus(
                        TransactionResponse.TransactionStatusDto.builder()
                                .name(transaction.getTransactionStatus().getName())
                                .build()
                )
                .value(transaction.getValue())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}