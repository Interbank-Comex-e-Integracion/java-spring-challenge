package com.ibk.transaction.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author aksandoval
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
  private final Logger LOGGER = LoggerFactory.getLogger(IllegalArgumentException.class);
  
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity illegalArgument(IllegalArgumentException ex) {
    LOGGER.error(ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
  }
  
}