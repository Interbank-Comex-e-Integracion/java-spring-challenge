package com.ibk.transaction.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author aksandoval
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "transaction_status")
public class TransactionStatusEntity {
  
  public static final short PENDING = 1;
  public static final short APPROVED = 2;
  public static final short REJECTED = 3;
  
  @Id
  private Short id;
  @Column(nullable = false)
  private String name;
  
}