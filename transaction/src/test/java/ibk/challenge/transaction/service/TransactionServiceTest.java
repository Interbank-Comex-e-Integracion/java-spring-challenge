package ibk.challenge.transaction.service;

import ibk.challenge.transaction.dto.CreateTransactionRequest;
import ibk.challenge.transaction.dto.CreateTransactionResponse;
import ibk.challenge.transaction.dto.GetTransactionInfoResponse;
import ibk.challenge.transaction.entity.Transaction;
import ibk.challenge.transaction.messages.RegisterMessage;
import ibk.challenge.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class TransactionServiceTest {
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    KafkaPublisherService kafkaPublisherService;
    @InjectMocks
    TransactionService transactionService;

    private UUID transactionExternalId;
    private Transaction mockedTransaction;


    @BeforeEach
    void setUp() {

        transactionExternalId = UUID.randomUUID();
        mockedTransaction = Transaction.builder()
                .transferTypeId(1)
                .transactionStatus(1)
                .transactionId(transactionExternalId)
                .accountExternalIdCredit("test")
                .accountExternalIdDebit("test")
                .value(500l)
                .build();

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTransactionOK() {

        CreateTransactionRequest request = CreateTransactionRequest.builder()
                .transferTypeId(1)
                .accountExternalIdDebit("test")
                .accountExternalIdCredit("test")
                .value(200l)
                .build();

        when(transactionRepository.save(any(Transaction.class))).thenReturn(mockedTransaction);

        CreateTransactionResponse result = transactionService.createTransaction(request);

        doNothing().when(kafkaPublisherService).sendValueInRegisterEvent(anyString(), any(RegisterMessage.class));

        Assertions.assertEquals(transactionExternalId.toString(),result.getTransactionExternalId().toString());
    }

    @Test
    void testGetTransactionByTransactionId() {
        when(transactionRepository.getTransactionByTransactionId(any(UUID.class))).thenReturn(Optional.of(mockedTransaction));

        GetTransactionInfoResponse result = transactionService.getTransactionByTransactionId(transactionExternalId);

        Assertions.assertEquals(result.getTransactionExternalId().toString(),transactionExternalId.toString());

    }

}
