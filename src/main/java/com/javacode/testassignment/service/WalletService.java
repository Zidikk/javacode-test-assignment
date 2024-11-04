package com.javacode.testassignment.service;

import com.javacode.testassignment.dao.dto.WalletDTO;
import com.javacode.testassignment.dao.mapper.IWalletMapper;
import com.javacode.testassignment.dao.model.Wallet;
import com.javacode.testassignment.dao.repository.IWalletRepository;
import com.javacode.testassignment.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class WalletService {

    private final IWalletRepository walletRepository;
    private final IWalletMapper walletMapper;

    @Autowired
    public WalletService(IWalletRepository walletRepository, IWalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
    }

    public WalletDTO getWallet(UUID walletId) {
        return walletRepository.findById(walletId)
                .map(walletMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with ID: " + walletId));
    }

    public WalletDTO performOperation(UUID walletId, String operationType, double amount) {
        Wallet existingWallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found with ID: " + walletId));

        if ("DEPOSIT".equalsIgnoreCase(operationType)) {
            existingWallet.setBalance(existingWallet.getBalance() + amount);
        } else if ("WITHDRAW".equalsIgnoreCase(operationType)) {
            if (existingWallet.getBalance() < amount) {
                throw new IllegalArgumentException("Insufficient funds for withdrawal.");
            }
            existingWallet.setBalance(existingWallet.getBalance() - amount);
        } else {
            throw new IllegalArgumentException("Invalid operation type. Use DEPOSIT or WITHDRAW.");
        }

        existingWallet = walletRepository.save(existingWallet);
        return walletMapper.toDTO(existingWallet);
    }
}