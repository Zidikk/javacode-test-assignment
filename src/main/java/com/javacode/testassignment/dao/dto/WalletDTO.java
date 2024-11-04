package com.javacode.testassignment.dao.dto;

import java.util.UUID;

public class WalletDTO {
    private UUID walletId;
    private double balance;

    public WalletDTO() {
    }

    public WalletDTO(UUID walletId, double balance) {
        this.walletId = walletId;
        this.balance = balance;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}