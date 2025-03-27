package com.ibk.challenge.repository;

import com.ibk.challenge.model.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, Long> {

    TransactionStatus findByName(String name);
}
