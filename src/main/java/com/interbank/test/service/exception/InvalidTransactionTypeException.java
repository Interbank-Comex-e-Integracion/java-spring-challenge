package com.interbank.test.service.exception;

public class InvalidTransactionTypeException extends RuntimeException {
        public InvalidTransactionTypeException(String message) {
                super(message);
        }
}
