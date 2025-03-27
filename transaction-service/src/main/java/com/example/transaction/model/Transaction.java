package com.example.transaction.model;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transactionExternalId;
    private UUID accountExternalIdDebit;
    private UUID accountExternalIdCredit;
    private int transferTypeId;
    private double value;
    private String transactionStatus;
    private LocalDateTime createdAt = LocalDateTime.now();
    
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
	public int getTransferTypeId() {
		return transferTypeId;
	}
	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
