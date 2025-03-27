package com.ibk.transaction.repository;

import com.ibk.transaction.model.TransactionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author aksandoval
 */
@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionTypeEntity, Short>{
  
}