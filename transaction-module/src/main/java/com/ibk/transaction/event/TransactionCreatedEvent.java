package com.ibk.transaction.event;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author aksandoval
 */
@Data
@Builder
public class TransactionCreatedEvent {
  
  private String id;
  private int value;
  private Instant createdAt; 
}