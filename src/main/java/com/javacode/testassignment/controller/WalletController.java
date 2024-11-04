package com.javacode.testassignment.controller;

import com.javacode.testassignment.dao.dto.OperationDTO;
import com.javacode.testassignment.dao.dto.WalletDTO;
import com.javacode.testassignment.exception.ResourceNotFoundException;
import com.javacode.testassignment.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")

public class WalletController {

    private final WalletService walletService;

    @Autowired
    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallet")
    public ResponseEntity<WalletDTO> performOperation(@RequestBody OperationDTO operationDTO) {
        WalletDTO updatedWallet = walletService.performOperation(operationDTO.getWalletId(), operationDTO.getOperationType(), operationDTO.getAmount());
        return ResponseEntity.ok(updatedWallet);
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<WalletDTO> getWallet(@PathVariable UUID walletId) {
        WalletDTO wallet = walletService.getWallet(walletId);
        return ResponseEntity.ok(wallet);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format.");
    }

}
