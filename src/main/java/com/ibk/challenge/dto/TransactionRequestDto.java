package com.ibk.challenge.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequestDto(
        UUID accountExternalIdDebit,
        UUID accountExternalIdCredit,
        BigDecimal value) {}