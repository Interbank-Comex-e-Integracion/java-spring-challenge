package com.ibk.transaction.service;

import com.ibk.transaction.event.TransactionCreatedEvent;
import com.ibk.transaction.model.TransactionEntity;
import com.ibk.transaction.model.TransactionStatusEntity;
import com.ibk.transaction.model.TransactionTypeEntity;
import com.ibk.transaction.repository.TransactionRepository;
import com.ibk.transaction.repository.TransactionStatusRepository;
import com.ibk.transaction.repository.TransactionTypeRepository;
import com.ibk.transaction.dto.GetTransactionResponse;
import com.ibk.transaction.dto.PostTransactionRequest;
import com.ibk.transaction.event.TransactionUpdatedEvent;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author aksandoval
 */
@Service
public class TransactionService {
  
  @Autowired
  private TransactionPublisher publisher;
  @Autowired
  private TransactionRepository transactionRepository;
  @Autowired
  private TransactionStatusRepository txnStatusRepository;
  @Autowired
  private TransactionTypeRepository txnTypeRepository;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
  
  public String storeTransaction(PostTransactionRequest request) {
    Instant ins = Instant.now();
    LOGGER.info("Store request for transaction with ID [{}] at [{}]", request.getTransferTypeId(), ins.toString());
    
    TransactionStatusEntity transactionStatus = txnStatusRepository.findById(TransactionStatusEntity.PENDING)
            .orElseThrow(() -> 
                new IllegalArgumentException("Transaction status identified by id [" + TransactionStatusEntity.PENDING + "] could not be found")
            );
    
    TransactionTypeEntity transactionType = txnTypeRepository.findById(request.getTransferTypeId())
            .orElseThrow(() -> 
                new IllegalArgumentException("Transaction type identified by id [" + request.getTransferTypeId() + "] could not be found")
            );
    
    TransactionEntity transaction = TransactionEntity.builder()
            .accountExternalIdCredit(request.getAccountExternalIdCredit())
            .accountExternalIdDebit(request.getAccountExternalIdDebit())
            .transactionType(transactionType)
            .value(request.getValue())
            .transactionStatus(transactionStatus)
            .createdAt(ins)
            .build();
    transaction = transactionRepository.save(transaction);
    String successMessage = "Transaction with ID [" + transaction.getTransactionExternalId() + "] was saved";
    LOGGER.info(successMessage);
    
    TransactionCreatedEvent event = TransactionCreatedEvent.builder()
            .id(transaction.getTransactionExternalId())
            .value(transaction.getValue())
            .createdAt(transaction.getCreatedAt())
            .build();
    publisher.sendTransactionCreatedEvent(event);
    return successMessage;
  }
  
  public void processTransactionUpdatedEvent(TransactionUpdatedEvent event) {
    LOGGER.info("Processing transaction with ID [{}] update at [{}]", event.getId(), Instant.now().toString());
    
    TransactionEntity transaction = transactionRepository.findById(event.getId())
            .orElseThrow(() -> 
                new IllegalArgumentException("Transaction with id [" + event.getId() + "] could not be found")
            );
    
    TransactionStatusEntity transactionStatus = txnStatusRepository.findById(event.getStatus())
            .orElseThrow(() -> 
                new IllegalArgumentException("Transaction status identified by id [" + event.getStatus() + "] could not be found")
            );
    
    transaction.setTransactionStatus(transactionStatus);
    transactionRepository.save(transaction);
    LOGGER.info("Transaction with ID [{}]'s status was updated to [{}]", transaction.getTransactionExternalId(), transactionStatus.getName());
  }
  
  public GetTransactionResponse getTransaction(String id) {
    LOGGER.info("Fetching transaction with ID [{}]", id);
    TransactionEntity transaction = transactionRepository.findById(id)
            .orElseThrow(() -> 
                new IllegalArgumentException("Transaction with id [" + id + "] could not be found")
            );
    GetTransactionResponse response = GetTransactionResponse.builder()
            .transactionExternalId(transaction.getTransactionExternalId())
            .transactionType(new GetTransactionResponse.TransactionType(transaction.getTransactionType().getName()))
            .transactionStatus(new GetTransactionResponse.TransactionStatus(transaction.getTransactionStatus().getName()))
            .value(transaction.getValue())
            .createdAt(transaction.getCreatedAt())
            .build();
    
    return response;
  }
}