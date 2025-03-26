package com.interbank.test.service;

import com.interbank.test.entity.Transaction;
import com.interbank.test.repository.TransactionRepository;
import com.interbank.test.service.dto.TransactionRequestDto;
import com.interbank.test.service.dto.TransactionResponseDto;
import com.interbank.test.service.dto.TransactionStatus;
import com.interbank.test.service.dto.TransactionStatusDto;
import com.interbank.test.service.dto.TransactionTypeDto;
import com.interbank.test.service.mapper.TransactionMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

        private final TransactionRepository transactionRepository;
        private final KafkaTemplate<String, String> kafkaTemplate;

        public Transaction createTransaction(TransactionRequestDto transactionRequestDto) {
                Transaction transaction = TransactionMapper.toEntity(transactionRequestDto);
                transaction.setTransactionStatus(transaction.getValue() > 1000 ? TransactionStatus.REJECTED : TransactionStatus.APPROVED);
                transaction.setCreatedAt(LocalDateTime.now());
                Transaction savedTransaction = transactionRepository.save(transaction);
                UUID uniqueKey = UUID.randomUUID();
                kafkaTemplate.send("transaction-status", uniqueKey.toString(), savedTransaction.getTransactionExternalId().toString());
                return savedTransaction;
        }

        public Optional<TransactionResponseDto> getTransactionByExternalId(UUID transactionExternalId) {
                return transactionRepository.findByTransactionExternalId(transactionExternalId)
                    .map(transaction -> {
                            TransactionTypeDto typeDTO = new TransactionTypeDto();
                            typeDTO.setName(transaction.getTransactionTypeId().name());

                            TransactionStatusDto statusDTO = new TransactionStatusDto();
                            statusDTO.setName(transaction.getTransactionStatus().name());

                            return TransactionResponseDto.builder()
                                .transactionExternalId(transaction.getTransactionExternalId().toString())
                                .value(transaction.getValue())
                                .createdAt(transaction.getCreatedAt().toString())
                                .transactionType(typeDTO)
                                .transactionStatus(statusDTO)
                                .build();
                    });
        }
}
