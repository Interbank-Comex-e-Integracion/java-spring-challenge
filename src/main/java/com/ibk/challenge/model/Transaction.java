package com.ibk.challenge.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction")
public class Transaction {

        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        @Column(name = "transaction_external_id", updatable = false, nullable = false)
        private UUID transactionExternalId;

        @NotNull
        @Column(name = "account_external_id_debit")
        private UUID accountExternalIdDebit;

        @NotNull
        @Column(name = "account_external_id_credit")
        private UUID accountExternalIdCredit;

        @ManyToOne
        @JoinColumn(name = "transfer_type_id")
        private TransactionType transactionType;

        @ManyToOne
        @JoinColumn(name = "transaction_status_id")
        private TransactionStatus transactionStatus;

        @NotNull
        @Column(name = "value")
        private BigDecimal value;

        @Column(name = "created_at")
        private OffsetDateTime createdAt;

        //Constructor sin argumentos.
        public Transaction() {}

        public Transaction(UUID transactionExternalId, UUID accountExternalIdDebit, UUID accountExternalIdCredit, TransactionType transactionType, TransactionStatus transactionStatus, BigDecimal value, OffsetDateTime createdAt) {
                this.transactionExternalId = transactionExternalId;
                this.accountExternalIdDebit = accountExternalIdDebit;
                this.accountExternalIdCredit = accountExternalIdCredit;
                this.transactionType = transactionType;
                this.transactionStatus = transactionStatus;
                this.value = value;
                this.createdAt = createdAt;
        }

        // Getters and setters
        public UUID getTransactionExternalId() {
                return transactionExternalId;
        }

        public void setTransactionExternalId(UUID transactionExternalId) {
                this.transactionExternalId = transactionExternalId;
        }

        public UUID getAccountExternalIdDebit() {
                return accountExternalIdDebit;
        }

        public void setAccountExternalIdDebit(UUID accountExternalIdDebit) {
                this.accountExternalIdDebit = accountExternalIdDebit;
        }

        public UUID getAccountExternalIdCredit() {
                return accountExternalIdCredit;
        }

        public void setAccountExternalIdCredit(UUID accountExternalIdCredit) {
                this.accountExternalIdCredit = accountExternalIdCredit;
        }

        public TransactionType getTransactionType() {
                return transactionType;
        }

        public void setTransactionType(TransactionType transactionType) {
                this.transactionType = transactionType;
        }

        public TransactionStatus getTransactionStatus() {
                return transactionStatus;
        }

        public void setTransactionStatus(TransactionStatus transactionStatus) {
                this.transactionStatus = transactionStatus;
        }

        public BigDecimal getValue() {
                return value;
        }

        public void setValue(BigDecimal value) {
                this.value = value;
        }

        public OffsetDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(OffsetDateTime createdAt) {
                this.createdAt = createdAt;
        }
}