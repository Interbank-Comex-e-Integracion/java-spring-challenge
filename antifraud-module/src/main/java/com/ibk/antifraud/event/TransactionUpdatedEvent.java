package com.ibk.antifraud.event;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author aksandoval
 */
@Data
@Builder
public class TransactionUpdatedEvent {
  
  private String id;
  private short status;
}