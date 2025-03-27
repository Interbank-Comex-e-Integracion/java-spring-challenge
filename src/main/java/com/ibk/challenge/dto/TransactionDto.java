package com.ibk.challenge.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record TransactionDto(
        UUID transactionExternalId,
        UUID accountExternalIdDebit,
        UUID accountExternalIdCredit,
        TransactionTypeDto transactionType,
        TransactionStatusDto transactionStatus,
        BigDecimal value,
        OffsetDateTime createdAt) {}
