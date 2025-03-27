package com.intercorp.challenge.ms_transaction.infrastructure.persistence;

import org.mapstruct.Mapper;

import com.intercorp.challenge.ms_transaction.domain.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toDomain(TransactionEntity transactionEntity);
    TransactionEntity toEntity(Transaction transaction);
}