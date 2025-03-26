package com.challenge.demo.service;

import com.challenge.demo.event.TransactionCreatedEvent;
import com.challenge.demo.event.TransactionStatusUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaService {
    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.transaction-created}")
    private String transactionCreatedTopic;

    @Value("${kafka.topic.transaction-status-updated}")
    private String transactionStatusUpdatedTopic;

    public KafkaService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransactionCreatedEvent(TransactionCreatedEvent event) {
        logger.info("Sending transaction created event: {}", event);
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(transactionCreatedTopic, event.getTransactionExternalId(), event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Transaction created event sent successfully for ID: {}",
                        event.getTransactionExternalId());
            } else {
                logger.error("Unable to send transaction created event for ID: {}",
                        event.getTransactionExternalId(), ex);
            }
        });
    }

    public void sendTransactionStatusUpdatedEvent(TransactionStatusUpdatedEvent event) {
        logger.info("Sending transaction status updated event: {}", event);
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(transactionStatusUpdatedTopic, event.getTransactionExternalId(), event);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Transaction status updated event sent successfully for ID: {}",
                        event.getTransactionExternalId());
            } else {
                logger.error("Unable to send transaction status updated event for ID: {}",
                        event.getTransactionExternalId(), ex);
            }
        });
    }
}
