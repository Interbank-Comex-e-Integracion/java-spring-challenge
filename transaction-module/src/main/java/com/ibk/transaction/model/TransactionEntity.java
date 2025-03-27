package com.ibk.transaction.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author aksandoval
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transactions")
public class TransactionEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(nullable = false, unique = true)
  private String transactionExternalId;

  @Column(nullable = false)
  private String accountExternalIdDebit;

  @Column(nullable = false)
  private String accountExternalIdCredit;

  @ManyToOne
  @JoinColumn(name = "transaction_type_id", nullable = false)
  private TransactionTypeEntity transactionType;

  @ManyToOne
  @JoinColumn(name = "transaction_status_id", nullable = false)
  private TransactionStatusEntity transactionStatus;

  private int value;
  
  @Column(nullable = false)
  private Instant createdAt;
}