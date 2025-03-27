package com.example.transaction.dto;

import java.util.UUID;

public class TransactionRequest {
    private UUID accountExternalIdDebit;
    private UUID accountExternalIdCredit;
    private int transferTypeId;
    private double value;
    
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

    // Getters y Setters
}
