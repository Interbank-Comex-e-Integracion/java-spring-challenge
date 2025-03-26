package com.interbank.test.controller.advice;

import com.interbank.test.service.exception.InvalidTransactionTypeException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(InvalidTransactionTypeException.class)
        public ResponseEntity<?> handleInvalidTransactionTypeException(InvalidTransactionTypeException ex, WebRequest request) {
                Map<String, String> response = new HashMap<>();
                response.put("error", ex.getMessage());
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
                Map<String, String> response = new HashMap<>();
                response.put("error", ex.getMessage());
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}