package com.challenge.demo.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionStatusUpdatedEvent {
    private String transactionExternalId;
    private Long transactionStatusId;
    private Instant updatedAt;
}