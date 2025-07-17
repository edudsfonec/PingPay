package com.pingpay.repository;

import com.pingpay.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


//    @Query("SELECT tc FROM Transaction tc WHERE tc.payer = :payer")
    List<Transaction> findTransactionsByPayer(Long walletId);

}
