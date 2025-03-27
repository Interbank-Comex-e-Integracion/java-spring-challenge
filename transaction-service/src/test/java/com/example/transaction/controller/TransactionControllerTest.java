package com.example.transaction.controller;

import com.example.transaction.dto.TransactionRequest;
import com.example.transaction.dto.TransactionResponse;
import com.example.transaction.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired ObjectMapper mapper;

    @MockBean
    private TransactionService transactionService;

    @Test
    void testUpdateTransactionStatus() throws Exception {
        UUID transactionId = UUID.randomUUID();
        String requestBody = "{\"status\":\"APPROVED\"}";

        TransactionResponse mockResponse = new TransactionResponse(transactionId, "APPROVED", 100.0, "2024-03-25T12:00:00");

        when(transactionService.updateTransactionStatus(eq(transactionId), anyString()))
            .thenReturn(Optional.of(mockResponse));

        mockMvc.perform(put("/transactions/" + transactionId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());
    }
    
    @Test
    void crearTransaccion_CuandoDatosSonValidos_DeberiaRetornarEstadoPendiente() throws Exception {
        UUID transactionId = UUID.randomUUID();
        TransactionRequest request = new TransactionRequest();
        request.setAccountExternalIdDebit(UUID.randomUUID());
        request.setAccountExternalIdCredit(UUID.randomUUID());
        request.setTransferTypeId(1);
        request.setValue(500.0);

        when(transactionService.createTransaction(any(TransactionRequest.class)))
            .thenReturn(new TransactionResponse(transactionId, "PENDING", 500.0, "2024-01-01T00:00:00"));

        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.transactionStatus").value("PENDING"));
    }
}
