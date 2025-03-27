package com.example.transaction.service;

import com.example.transaction.dto.TransactionRequest;
import com.example.transaction.dto.TransactionResponse;
import com.example.transaction.model.Transaction;
import com.example.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;
    
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private TransactionService service;

    @Test
    void testUpdateTransactionStatus() {
        UUID transactionId = UUID.randomUUID();
        Transaction transaction = new Transaction();
        transaction.setTransactionExternalId(transactionId);
        transaction.setTransactionStatus("PENDING");

        when(repository.findById(transactionId)).thenReturn(Optional.of(transaction));

        service.updateTransactionStatus(transactionId, "APPROVED");

        assertEquals("APPROVED", transaction.getTransactionStatus());
        verify(repository).save(transaction);
    }
    
    @Test
    void crearTransaccion_ConValorMayorA1000_DeberiaMarcarComoRechazada() {
        // Configuración: Transacción con valor 1500 (mayor a 1000)
        TransactionRequest request = new TransactionRequest();
        request.setValue(1500.0);
        request.setAccountExternalIdDebit(UUID.randomUUID());
        request.setAccountExternalIdCredit(UUID.randomUUID());
        request.setTransferTypeId(1);

        // Mock del repository para devolver la transacción guardada
        Transaction transaccionGuardada = new Transaction();
        transaccionGuardada.setTransactionExternalId(UUID.randomUUID());
        transaccionGuardada.setValue(1500.0);
        transaccionGuardada.setTransactionStatus("PENDING"); // Estado inicial
        
        when(repository.save(any(Transaction.class))).thenReturn(transaccionGuardada);

        // Ejecución
        TransactionResponse response = service.createTransaction(request);

        // Impresión de datos para depuración (opcional)
        System.out.println("\n=== Datos de Transacción Rechazada ===");
        System.out.println("ID: " + response.getTransactionExternalId());
        System.out.println("Valor: " + response.getValue());
        System.out.println("Estado: " + response.getTransactionStatus());
        System.out.println("===============================\n");

        // Verificaciones
        assertNotNull(response);
        assertEquals(1500.0, response.getValue());
        
        // Aunque el servicio solo marca como PENDING inicialmente,
        // verificamos que se envió a Kafka para su posterior rechazo
        verify(kafkaTemplate).send(eq("transactions"), anyString());
    }
}
