package com.ibk.challenge.repository;

import com.ibk.challenge.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {

    TransactionType findByName(String name);
}
