package com.javacode.testassignment.dao.repository;

import com.javacode.testassignment.dao.model.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IWalletRepository extends JpaRepository<Wallet, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Override
    @NonNull
    Optional<Wallet> findById(@NonNull UUID walletId);
}
