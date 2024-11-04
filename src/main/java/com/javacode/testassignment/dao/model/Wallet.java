package com.javacode.testassignment.dao.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "wallet_id", nullable = false)
    private UUID walletId;

    @Column(name = "balance", nullable = false)
    private double balance;

    public Wallet() {
    }

    public Wallet(double balance) {
        this.balance = balance;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wallet wallet)) return false;
        return Double.compare(getBalance(), wallet.getBalance()) == 0 && Objects.equals(getWalletId(), wallet.getWalletId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWalletId(), getBalance());
    }
}
