package com.ibk.transaction.controller;

import com.ibk.transaction.dto.GetTransactionResponse;
import com.ibk.transaction.dto.PostTransactionRequest;
import com.ibk.transaction.service.TransactionPublisher;
import com.ibk.transaction.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aksandoval
 */
@RestController
public class TransactionController {
  
  @Autowired
  private TransactionService service;
  
  @PostMapping("/transactions")
  public ResponseEntity<?> publishTransaction(@RequestBody PostTransactionRequest request) throws IllegalArgumentException {
    String message = service.storeTransaction(request);
    return ResponseEntity.ok(message);
  }
  
  @GetMapping("/transactions/{id}")
  public ResponseEntity<GetTransactionResponse> getTransaction(@PathVariable String id) throws IllegalArgumentException {
    GetTransactionResponse response = service.getTransaction(id);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}