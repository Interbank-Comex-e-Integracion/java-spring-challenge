package com.interbank.test.service.mapper;

import com.interbank.test.entity.Transaction;
import com.interbank.test.service.dto.TransactionRequestDto;
import com.interbank.test.service.dto.TransactionType;
import com.interbank.test.service.exception.InvalidTransactionTypeException;

public class TransactionMapper {

        public static Transaction toEntity(TransactionRequestDto dto) {
                Transaction transaction = new Transaction();
                transaction.setAccountExternalIdDebit(dto.getAccountExternalIdDebit());
                transaction.setAccountExternalIdCredit(dto.getAccountExternalIdCredit());
                try {
                        transaction.setTransactionTypeId(TransactionType.fromId(dto.getTransactionTypeId()));
                } catch (IllegalArgumentException e) {
                        throw new InvalidTransactionTypeException("Invalid transaction type ID: " + dto.getTransactionTypeId());
                }
                transaction.setValue(dto.getValue());
                return transaction;
        }
}