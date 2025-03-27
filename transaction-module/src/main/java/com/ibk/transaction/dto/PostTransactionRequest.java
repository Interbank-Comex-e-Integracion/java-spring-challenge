package com.ibk.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 *
 * @author aksandoval
 */
@Getter
@AllArgsConstructor
public class PostTransactionRequest {
  
  @NonNull
  private final String accountExternalIdDebit;
  @NonNull
  private final String accountExternalIdCredit;
  private final short transferTypeId;
  private final int value;
  
}