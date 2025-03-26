package com.interbank.test.service.dto;

public enum TransactionType {
        TRANSFER(1),
        PAYMENT(2);

        private final int id;

        TransactionType(int id) {
                this.id = id;
        }

        public int getId() {
                return id;
        }

        public static TransactionType fromId(int id) {
                for (TransactionType type : values()) {
                        if (type.getId() == id) {
                                return type;
                        }
                }
                throw new IllegalArgumentException("Invalid transaction type ID: " + id);
        }
}
