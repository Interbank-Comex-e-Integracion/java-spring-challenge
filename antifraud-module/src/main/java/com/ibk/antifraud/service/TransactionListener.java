package com.ibk.antifraud.service;

import com.ibk.antifraud.event.TransactionCreatedEvent;
import com.ibk.antifraud.event.TransactionUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author aksandoval
 */
@Service
public class TransactionListener {
  
  private static short APPROVED_STATUS = 2;
  private static short REJECTED_STATUS = 3;
  
  @Autowired
  private TransactionPublisher publisher;
  
  Logger LOGGER = LoggerFactory.getLogger(TransactionListener.class);
  
  @KafkaListener(topics = "transaction-created", groupId = "antifraud")
  public void consumeTransactionCreatedEvent(TransactionCreatedEvent createdEvent) {
    LOGGER.info("Transaction with ID [{}] recieved", createdEvent.getId());
    
    short transactionStatus;
    if (createdEvent.getValue() <= 1000) {
      LOGGER.info("Transaction with ID [{}] approved", createdEvent.getId());
      transactionStatus = APPROVED_STATUS;
    }
    else {
      LOGGER.info("Transaction with ID [{}] rejected. Reason: Value {} exceeds threshold", createdEvent.getId(), createdEvent.getValue());
      transactionStatus = REJECTED_STATUS;
    }
    
    TransactionUpdatedEvent updatedEvent = TransactionUpdatedEvent.builder()
            .id(createdEvent.getId())
            .status(transactionStatus)
            .build();
    publisher.sendTransactionUpdatedEvent(updatedEvent);
  }
}