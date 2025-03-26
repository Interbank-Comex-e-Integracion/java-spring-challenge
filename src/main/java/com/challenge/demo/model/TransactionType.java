package com.challenge.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionType {
    @Id
    private Long id;
    private String name;
}
