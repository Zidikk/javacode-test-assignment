package com.javacode.testassignment.miisc;

import com.javacode.testassignment.dao.model.Wallet;
import com.javacode.testassignment.dao.repository.IWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IWalletRepository walletRepository;

    @Autowired
    public DataInitializer(IWalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public void run(String... args) {
        List<Wallet> existingWallets = walletRepository.findAll();
        if (existingWallets.isEmpty()) {
            for (int i = 0; i < 10; i++) {
                Wallet wallet = new Wallet(1000);
                walletRepository.save(wallet);
                System.out.println("Created wallet with ID: " + wallet.getWalletId());
            }
        } else {
            System.out.println("Wallets already exist in the database. Skipping creation.");
        }
    }
}
