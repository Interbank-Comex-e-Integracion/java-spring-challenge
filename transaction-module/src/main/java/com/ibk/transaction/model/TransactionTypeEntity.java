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
@Table(name = "transaction_type")
public class TransactionTypeEntity {
  
  @Id
  private Short id;
  @Column(nullable = false)
  private String name;
  
}