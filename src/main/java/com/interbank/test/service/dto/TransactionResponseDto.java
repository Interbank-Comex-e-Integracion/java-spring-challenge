package com.interbank.test.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@Builder
@ToString
public class TransactionResponseDto {

        private String transactionExternalId;
        private TransactionTypeDto transactionType;
        private TransactionStatusDto transactionStatus;
        private Integer value;
        private String createdAt;

}
