package com.javacode.testassignment.dao.dto;

import java.util.UUID;

public class OperationDTO {
    private UUID walletId;
    private String operationType;
    private double amount;

    public OperationDTO() {
    }

    public OperationDTO(UUID walletId, String operationType, double amount) {
        this.walletId = walletId;
        this.operationType = operationType;
        this.amount = amount;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
