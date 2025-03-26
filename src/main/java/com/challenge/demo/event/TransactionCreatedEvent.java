package com.challenge.demo.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionCreatedEvent {
    private String transactionExternalId;
    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private Long transactionTypeId;
    private Long transactionStatusId;
    private BigDecimal value;
    private Instant createdAt;
}