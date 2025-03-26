package com.challenge.demo.service;

import com.challenge.demo.dto.CreateTransactionRequest;
import com.challenge.demo.dto.TransactionResponse;
import com.challenge.demo.event.TransactionStatusUpdatedEvent;
import com.challenge.demo.exception.TransactionNotFoundException;
import com.challenge.demo.model.Transaction;
import com.challenge.demo.model.TransactionStatus;
import com.challenge.demo.model.TransactionType;
import com.challenge.demo.repository.TransactionRepository;
import com.challenge.demo.repository.TransactionStatusRepository;
import com.challenge.demo.repository.TransactionTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionTypeRepository transactionTypeRepository;

    @Mock
    private TransactionStatusRepository transactionStatusRepository;

    @Mock
    private KafkaService kafkaService;

    @InjectMocks
    private TransactionService transactionService;

    private TransactionType mockTransactionType;
    private TransactionStatus mockPendingStatus;
    private TransactionStatus mockApprovedStatus;
    private Transaction mockTransaction;
    private String transactionExternalId;

    @BeforeEach
    void setUp() {

        transactionExternalId = UUID.randomUUID().toString();

        mockTransactionType = new TransactionType(1L, "Transfer");

        mockPendingStatus = new TransactionStatus(1L, "Pending");
        mockApprovedStatus = new TransactionStatus(2L, "Approved");

        mockTransaction = Transaction.builder()
                .id(1L)
                .transactionExternalId(transactionExternalId)
                .accountExternalIdDebit("debit-account-id")
                .accountExternalIdCredit("credit-account-id")
                .transactionType(mockTransactionType)
                .transactionStatus(mockPendingStatus)
                .value(new BigDecimal("500"))
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void createTransaction_Success() {

        CreateTransactionRequest request = new CreateTransactionRequest(
                "debit-account-id",
                "credit-account-id",
                1L,
                new BigDecimal("500")
        );

        when(transactionTypeRepository.findById(1L)).thenReturn(Optional.of(mockTransactionType));
        when(transactionStatusRepository.findById(TransactionStatus.PENDING)).thenReturn(Optional.of(mockPendingStatus));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockTransaction);
        doNothing().when(kafkaService).sendTransactionCreatedEvent(any());

        TransactionResponse response = transactionService.createTransaction(request);

        assertNotNull(response);
        assertEquals("Transfer", response.getTransactionType().getName());
        assertEquals("Pending", response.getTransactionStatus().getName());
        assertEquals(new BigDecimal("500"), response.getValue());

        verify(transactionRepository).save(any(Transaction.class));
        verify(kafkaService).sendTransactionCreatedEvent(any());
    }

    @Test
    void getTransaction_Success() {

        when(transactionRepository.findByTransactionExternalId(transactionExternalId))
                .thenReturn(Optional.of(mockTransaction));

        TransactionResponse response = transactionService.getTransaction(transactionExternalId);

        assertNotNull(response);
        assertEquals(transactionExternalId, response.getTransactionExternalId());
        assertEquals("Transfer", response.getTransactionType().getName());
        assertEquals("Pending", response.getTransactionStatus().getName());
        assertEquals(new BigDecimal("500"), response.getValue());
    }

    @Test
    void getTransaction_NotFound() {
        String nonExistentId = "non-existent-id";
        when(transactionRepository.findByTransactionExternalId(nonExistentId))
                .thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> {
            transactionService.getTransaction(nonExistentId);
        });
    }

    @Test
    void updateTransactionStatus_Success() {

        TransactionStatusUpdatedEvent event = new TransactionStatusUpdatedEvent(
                transactionExternalId,
                TransactionStatus.APPROVED,
                Instant.now()
        );

        when(transactionRepository.findByTransactionExternalId(transactionExternalId))
                .thenReturn(Optional.of(mockTransaction));
        when(transactionStatusRepository.findById(TransactionStatus.APPROVED))
                .thenReturn(Optional.of(mockApprovedStatus));

        transactionService.updateTransactionStatus(event);

        verify(transactionRepository).findByTransactionExternalId(transactionExternalId);
        verify(transactionStatusRepository).findById(TransactionStatus.APPROVED);
        verify(transactionRepository).save(mockTransaction);
        assertEquals(mockApprovedStatus, mockTransaction.getTransactionStatus());
    }
}