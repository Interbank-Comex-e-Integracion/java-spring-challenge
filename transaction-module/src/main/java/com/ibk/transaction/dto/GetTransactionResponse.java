package com.ibk.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import lombok.Builder;

/**
 *
 * @author aksandoval
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetTransactionResponse {

  private String transactionExternalId;
  private TransactionType transactionType;
  private TransactionStatus transactionStatus;
  private int value;
  private Instant createdAt;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class TransactionType {
    private String name;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class TransactionStatus {
    private String name;
  }
}
