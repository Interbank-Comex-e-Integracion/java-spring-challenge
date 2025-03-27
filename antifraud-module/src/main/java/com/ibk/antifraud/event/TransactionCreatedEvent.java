package com.ibk.antifraud.event;

import java.time.Instant;
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
public class TransactionCreatedEvent {
  
  private String id;
  private int value;
  private Instant createdAt; 
}