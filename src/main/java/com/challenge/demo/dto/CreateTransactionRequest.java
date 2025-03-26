package com.challenge.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {
    @NotBlank(message = "Account External ID Debit is required")
    private String accountExternalIdDebit;

    @NotBlank(message = "Account External ID Credit is required")
    private String accountExternalIdCredit;

    @NotNull(message = "Transfer Type ID is required")
    private Long tranferTypeId;

    @NotNull(message = "Value is required")
    @Positive(message = "Value must be positive")
    private BigDecimal value;
}
