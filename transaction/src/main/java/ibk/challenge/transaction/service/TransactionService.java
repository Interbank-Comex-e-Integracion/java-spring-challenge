package ibk.challenge.transaction.service;

import com.google.gson.Gson;
import ibk.challenge.transaction.definition.TransactionStatusDefinition;
import ibk.challenge.transaction.dto.CreateTransactionRequest;
import ibk.challenge.transaction.dto.CreateTransactionResponse;
import ibk.challenge.transaction.dto.GetTransactionInfoResponse;
import ibk.challenge.transaction.entity.Transaction;
import ibk.challenge.transaction.messages.RegisterMessage;
import ibk.challenge.transaction.messages.UpdateStatusMessage;
import ibk.challenge.transaction.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    static Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
    static Gson GSON = new Gson();

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private KafkaPublisherService kafkaPublisherService;

    public CreateTransactionResponse createTransaction(CreateTransactionRequest transactionRequest) {

        Transaction newTransaction = Transaction.builder()
                .accountExternalIdCredit(transactionRequest.getAccountExternalIdCredit())
                .accountExternalIdDebit(transactionRequest.getAccountExternalIdDebit())
                .transferTypeId(transactionRequest.getTransferTypeId())
                .value(transactionRequest.getValue())
                .transactionStatus(TransactionStatusDefinition.PENDING_STATUS)
                .build();

        Transaction savedTransaction = transactionRepository.save(newTransaction);
        RegisterMessage registerMessage = new RegisterMessage(savedTransaction.getTransactionId().toString(),savedTransaction.getValue());
        kafkaPublisherService.sendValueInRegisterEvent("antifraud-register",registerMessage);

        CreateTransactionResponse response = new CreateTransactionResponse(savedTransaction.getTransactionId().toString());
        return response;
    }

    public GetTransactionInfoResponse getTransactionByTransactionId(UUID transactionId) {
        Optional<Transaction> transactionResult = transactionRepository.getTransactionByTransactionId(transactionId);

        if (transactionResult.isEmpty())
            throw new EntityNotFoundException(String.format("Transaction [%s] not found",transactionId.toString()));

        Transaction transaction = transactionResult.get();

        GetTransactionInfoResponse response = GetTransactionInfoResponse.builder()
                .transactionExternalId(transaction.getTransactionId().toString())
                .transactionType(GetTransactionInfoResponse.mapTransactionType(transaction.getTransferTypeId()))
                .transactionStatus(GetTransactionInfoResponse.mapTransactionStatus(transaction.getTransactionStatus()))
                .value(transaction.getValue())
                .createdAt(transaction.getCreatedDate())
                .build();

        return response;
    }

    @Transactional
    @KafkaListener(topics = "antifraud-update-status", groupId = "antifraud-group")
    public void updateTransactionStatusByUpdateStatusEvent(ConsumerRecord message)
    {
        UpdateStatusMessage updateMessage = GSON.fromJson(message.value().toString(),UpdateStatusMessage.class);
        Integer updateResult = transactionRepository.updateStatusByTransactionId(UUID.fromString(updateMessage.getTransactionId()),updateMessage.getTransactionalStatus());
        if (updateResult <= 0)
        {
            LOGGER.error("Transaction [{}] status update failed",updateMessage.getTransactionId());
        }
        LOGGER.info("Transaction [{}] status update succed",updateMessage.getTransactionId());
    }

}
