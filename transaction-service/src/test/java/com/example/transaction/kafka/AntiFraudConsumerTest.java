package com.example.transaction.kafka;

import com.example.transaction.model.Transaction;
import com.example.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AntiFraudConsumerTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private AntiFraudConsumer antiFraudConsumer;

    @Test
    void procesarTransaccion_ValorMayorA1000_DeberiaRechazar() {
        // Configuración
        UUID transactionId = UUID.randomUUID();
        Transaction transaccion = new Transaction();
        transaccion.setTransactionExternalId(transactionId);
        transaccion.setValue(1500.0); // Valor que supera el límite
        transaccion.setTransactionStatus("PENDING");

        when(repository.findById(transactionId)).thenReturn(Optional.of(transaccion));

        // Ejecución
        antiFraudConsumer.listen(transactionId.toString());

        // Verificación
        verify(repository).save(argThat(tx -> 
            tx.getTransactionStatus().equals("REJECTED") // Estado actualizado
        ));
        verify(kafkaProducer).sendTransactionStatus(transactionId, "REJECTED");
    }

    @Test
    void procesarTransaccion_ValorMenorIgualA1000_DeberiaAprobar() {
        // Configuración
        UUID transactionId = UUID.randomUUID();
        Transaction transaccion = new Transaction();
        transaccion.setTransactionExternalId(transactionId);
        transaccion.setValue(1000.0); // Valor dentro del límite
        transaccion.setTransactionStatus("PENDING");

        when(repository.findById(transactionId)).thenReturn(Optional.of(transaccion));

        // Ejecución
        antiFraudConsumer.listen(transactionId.toString());

        // Verificación
        verify(repository).save(argThat(tx -> 
            tx.getTransactionStatus().equals("APPROVED") // Estado actualizado
        ));
        verify(kafkaProducer).sendTransactionStatus(transactionId, "APPROVED");
    }
}