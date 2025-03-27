package com.ibk.transaction.repository;

import com.ibk.transaction.model.TransactionStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author aksandoval
 */
@Repository
public interface TransactionStatusRepository extends JpaRepository<TransactionStatusEntity, Short>{
  
}