package com.pingpay.repository;

import com.pingpay.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    @Query("SELECT w FROM WALLETS w WHERE w.email = :email")
    Optional<Wallet> findByEmail(String email);

    Optional<Wallet> findByCpf(Long cpf);

}
