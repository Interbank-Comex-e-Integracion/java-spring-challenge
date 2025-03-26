package com.challenge.demo.controller;

import com.challenge.demo.dto.CreateTransactionRequest;
import com.challenge.demo.dto.TransactionResponse;
import com.challenge.demo.exception.TransactionNotFoundException;
import com.challenge.demo.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private TransactionService transactionService;

    @Test
    void createTransaction_Success() throws Exception {

        CreateTransactionRequest request = new CreateTransactionRequest(
                "debit-account-id",
                "credit-account-id",
                1L,
                new BigDecimal("500")
        );

        String transactionExternalId = UUID.randomUUID().toString();
        Instant now = Instant.now();

        TransactionResponse response = TransactionResponse.builder()
                .transactionExternalId(transactionExternalId)
                .transactionType(new TransactionResponse.TransactionTypeDto("Transfer"))
                .transactionStatus(new TransactionResponse.TransactionStatusDto("Pending"))
                .value(new BigDecimal("500"))
                .createdAt(now)
                .build();

        when(transactionService.createTransaction(any(CreateTransactionRequest.class))).thenReturn(response);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionExternalId").value(transactionExternalId))
                .andExpect(jsonPath("$.transactionType.name").value("Transfer"))
                .andExpect(jsonPath("$.transactionStatus.name").value("Pending"))
                .andExpect(jsonPath("$.value").value(500));
    }

    @Test
    void createTransaction_BadRequest() throws Exception {
        CreateTransactionRequest request = new CreateTransactionRequest(
                "",
                "credit-account-id",
                1L,
                new BigDecimal("500")
        );

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getTransaction_Success() throws Exception {

        String transactionExternalId = UUID.randomUUID().toString();
        Instant now = Instant.now();

        TransactionResponse response = TransactionResponse.builder()
                .transactionExternalId(transactionExternalId)
                .transactionType(new TransactionResponse.TransactionTypeDto("Transfer"))
                .transactionStatus(new TransactionResponse.TransactionStatusDto("Approved"))
                .value(new BigDecimal("500"))
                .createdAt(now)
                .build();

        when(transactionService.getTransaction(eq(transactionExternalId))).thenReturn(response);

        mockMvc.perform(get("/transactions/" + transactionExternalId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionExternalId").value(transactionExternalId))
                .andExpect(jsonPath("$.transactionType.name").value("Transfer"))
                .andExpect(jsonPath("$.transactionStatus.name").value("Approved"))
                .andExpect(jsonPath("$.value").value(500));
    }

    @Test
    void getTransaction_NotFound() throws Exception {

        String transactionExternalId = UUID.randomUUID().toString();
        when(transactionService.getTransaction(eq(transactionExternalId)))
                .thenThrow(new TransactionNotFoundException(transactionExternalId));

        mockMvc.perform(get("/transactions/" + transactionExternalId))
                .andExpect(status().isNotFound());
    }
}