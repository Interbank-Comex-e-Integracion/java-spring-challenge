package com.interbank.test.service.dto;

import java.util.UUID;
import lombok.Data;

@Data
public class TransactionRequestDto {
        private UUID accountExternalIdDebit;
        private UUID accountExternalIdCredit;
        private Integer transactionTypeId;
        private Integer value;
}
