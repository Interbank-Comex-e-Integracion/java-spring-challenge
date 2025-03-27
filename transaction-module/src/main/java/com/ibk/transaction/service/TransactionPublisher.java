package com.ibk.transaction.service;

import com.ibk.transaction.event.TransactionCreatedEvent;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

/**
 *
 * @author aksandoval
 */
@Service
public class TransactionPublisher {
  
  @Autowired
  private KafkaTemplate<String, TransactionCreatedEvent> template;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionPublisher.class);
  
  public void sendTransactionCreatedEvent(TransactionCreatedEvent event) {
    LOGGER.info("Attempting to send transaction created event with ID [{}]", event.getId());
    CompletableFuture<SendResult<String, TransactionCreatedEvent>> future = template.send("transaction-created", event);
    future.whenComplete((result, ex) -> {
      if (ex == null)
        LOGGER.info("Transaction created event with ID [{}] was sent", event.getId());
      else
        LOGGER.error("Transaction created event with ID [{}] could not be sent", event.getId(), ex);
    });
  }
}