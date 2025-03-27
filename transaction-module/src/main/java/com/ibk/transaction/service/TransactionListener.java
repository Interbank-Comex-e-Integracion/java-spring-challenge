package com.ibk.transaction.service;

import com.ibk.transaction.event.TransactionUpdatedEvent;
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
  
  @Autowired
  private TransactionService service;
  
  Logger LOGGER = LoggerFactory.getLogger(TransactionListener.class);
  
  @KafkaListener(topics = "transaction-updated", groupId = "antifraud")
  public void consumeTransactionUpdatedEvent(TransactionUpdatedEvent event) {
    LOGGER.info("Transaction with ID [{}] update recieved", event.getId());
    service.processTransactionUpdatedEvent(event);
  }
}