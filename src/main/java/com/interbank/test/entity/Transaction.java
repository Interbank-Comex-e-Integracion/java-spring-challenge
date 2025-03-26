package com.interbank.test.entity;

import com.interbank.test.service.dto.TransactionStatus;
import com.interbank.test.service.dto.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Entity
@Data
public class Transaction {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID transactionExternalId;

        private UUID accountExternalIdDebit;
        private UUID accountExternalIdCredit;
        @Enumerated(EnumType.STRING)
        private TransactionType transactionTypeId;
        private Integer value;
        @Enumerated(EnumType.STRING)
        private TransactionStatus transactionStatus;
        private LocalDateTime createdAt;
}
