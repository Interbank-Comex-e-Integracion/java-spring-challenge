package com.example.transaction.dto;

import java.util.UUID;

public class TransactionResponse {
    private UUID transactionExternalId;
    private String transactionStatus;
    private double value;
    private String createdAt;
    
    
	public TransactionResponse(UUID transactionExternalId, String transactionStatus, double value, String createdAt) {
		super();
		this.transactionExternalId = transactionExternalId;
		this.transactionStatus = transactionStatus;
		this.value = value;
		this.createdAt = createdAt;
	}
	public UUID getTransactionExternalId() {
		return transactionExternalId;
	}
	public void setTransactionExternalId(UUID transactionExternalId) {
		this.transactionExternalId = transactionExternalId;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

}
