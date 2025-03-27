package com.ibk.transaction.repository;

import com.ibk.transaction.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author aksandoval
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String>{
  
}