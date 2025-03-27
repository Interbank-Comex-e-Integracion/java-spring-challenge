package com.ibk.antifraud.service;

import com.ibk.antifraud.event.TransactionUpdatedEvent;
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
  private KafkaTemplate<String, TransactionUpdatedEvent> template;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionPublisher.class);
  
  public void sendTransactionUpdatedEvent(TransactionUpdatedEvent event) {
    LOGGER.info("Attempting to send transaction updated event with ID [{}]", event.getId());
    CompletableFuture<SendResult<String, TransactionUpdatedEvent>> future = template.send("transaction-updated", event);
    future.whenComplete((result, ex) -> {
      if (ex == null)
        LOGGER.info("Transaction updated event with ID [{}] was sent", event.getId());
      else
        LOGGER.error("Transaction updated event with ID [{}] could not be sent", event.getId(), ex);
    });
  }
}