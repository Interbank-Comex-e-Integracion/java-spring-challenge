package ibk.challenge.transaction.repository;

import ibk.challenge.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    public Optional<Transaction> getTransactionByTransactionId(UUID id);

    @Modifying
    @Query("UPDATE Transaction e SET e.transactionStatus = :status WHERE e.transactionId = :id")
    public int updateStatusByTransactionId(@Param("id") UUID id, @Param("status") Integer status);
}
