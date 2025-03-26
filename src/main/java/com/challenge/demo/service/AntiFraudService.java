package com.challenge.demo.service;

import com.challenge.demo.event.TransactionCreatedEvent;
import com.challenge.demo.event.TransactionStatusUpdatedEvent;
import com.challenge.demo.model.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class AntiFraudService {
    private static final Logger logger = LoggerFactory.getLogger(AntiFraudService.class);

    private final KafkaService kafkaService;
    private final BigDecimal MAX_ALLOWED_VALUE = new BigDecimal("1000");

    public AntiFraudService(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @KafkaListener(topics = "${kafka.topic.transaction-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void processTransaction(TransactionCreatedEvent event) {
        logger.info("Processing transaction for anti-fraud: {}", event);

        long newStatusId;
        if (event.getValue().compareTo(MAX_ALLOWED_VALUE) > 0) {
            logger.info("Transaction {} rejected: value {} exceeds maximum allowed {}",
                    event.getTransactionExternalId(), event.getValue(), MAX_ALLOWED_VALUE);
            newStatusId = TransactionStatus.REJECTED;
        } else {
            logger.info("Transaction {} approved: value {} is within allowed limits",
                    event.getTransactionExternalId(), event.getValue());
            newStatusId = TransactionStatus.APPROVED;
        }

        TransactionStatusUpdatedEvent statusEvent = TransactionStatusUpdatedEvent.builder()
                .transactionExternalId(event.getTransactionExternalId())
                .transactionStatusId(newStatusId)
                .updatedAt(Instant.now())
                .build();

        kafkaService.sendTransactionStatusUpdatedEvent(statusEvent);
    }
}