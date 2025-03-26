package com.challenge.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionStatus {
    @Id
    private Long id;
    private String name;

    public static final long PENDING = 1L;
    public static final long APPROVED = 2L;
    public static final long REJECTED = 3L;
}