package com.ibk.transaction.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author aksandoval
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionUpdatedEvent {
  
  private String id;
  private short status;
}