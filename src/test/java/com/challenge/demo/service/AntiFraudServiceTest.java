package com.challenge.demo.service;

import com.challenge.demo.event.TransactionCreatedEvent;
import com.challenge.demo.event.TransactionStatusUpdatedEvent;
import com.challenge.demo.model.TransactionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AntiFraudServiceTest {

    @Mock
    private KafkaService kafkaService;

    @InjectMocks
    private AntiFraudService antiFraudService;

    @Captor
    private ArgumentCaptor<TransactionStatusUpdatedEvent> eventCaptor;

    private String transactionExternalId;
    private TransactionCreatedEvent validTransaction;
    private TransactionCreatedEvent invalidTransaction;

    @BeforeEach
    void setUp() {
        transactionExternalId = UUID.randomUUID().toString();

        validTransaction = TransactionCreatedEvent.builder()
                .transactionExternalId(transactionExternalId)
                .accountExternalIdDebit("debit-account-id")
                .accountExternalIdCredit("credit-account-id")
                .transactionTypeId(1L)
                .transactionStatusId(TransactionStatus.PENDING)
                .value(new BigDecimal("500"))
                .createdAt(Instant.now())
                .build();

        invalidTransaction = TransactionCreatedEvent.builder()
                .transactionExternalId(transactionExternalId)
                .accountExternalIdDebit("debit-account-id")
                .accountExternalIdCredit("credit-account-id")
                .transactionTypeId(1L)
                .transactionStatusId(TransactionStatus.PENDING)
                .value(new BigDecimal("1500"))
                .createdAt(Instant.now())
                .build();
    }

    @Test
    void processTransaction_ShouldApprove_WhenValueIsLessThan1000() {

        antiFraudService.processTransaction(validTransaction);

        verify(kafkaService).sendTransactionStatusUpdatedEvent(eventCaptor.capture());
        TransactionStatusUpdatedEvent sentEvent = eventCaptor.getValue();

        assertEquals(transactionExternalId, sentEvent.getTransactionExternalId());
        assertEquals(TransactionStatus.APPROVED, sentEvent.getTransactionStatusId());
    }

    @Test
    void processTransaction_ShouldReject_WhenValueIsMoreThan1000() {

        antiFraudService.processTransaction(invalidTransaction);

        verify(kafkaService).sendTransactionStatusUpdatedEvent(eventCaptor.capture());
        TransactionStatusUpdatedEvent sentEvent = eventCaptor.getValue();

        assertEquals(transactionExternalId, sentEvent.getTransactionExternalId());
        assertEquals(TransactionStatus.REJECTED, sentEvent.getTransactionStatusId());
    }
}