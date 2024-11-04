package com.javacode.testassignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.testassignment.controller.WalletController;
import com.javacode.testassignment.dao.dto.OperationDTO;
import com.javacode.testassignment.dao.dto.WalletDTO;
import com.javacode.testassignment.exception.ResourceNotFoundException;
import com.javacode.testassignment.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WalletService walletService;

    @InjectMocks
    private WalletController walletController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(walletController).build();
    }

    @Test
    public void testPerformOperation_WalletNotFound() throws Exception {
        UUID walletId = UUID.randomUUID();
        double operationAmount = 100.0;

        when(walletService.performOperation(walletId, "DEPOSIT", operationAmount))
                .thenThrow(new ResourceNotFoundException("Wallet not found with ID: " + walletId));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OperationDTO(walletId, "DEPOSIT", operationAmount))))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Wallet not found with ID: " + walletId));
    }

    @Test
    public void testPerformOperation() throws Exception {
        UUID walletId = UUID.randomUUID();
        double initialBalance = 1000.0;
        double operationAmount = 100.0;
        WalletDTO updatedWallet = new WalletDTO(walletId, initialBalance + operationAmount);

        when(walletService.performOperation(walletId, "DEPOSIT", operationAmount)).thenReturn(updatedWallet);

        OperationDTO operationDTO = new OperationDTO(walletId, "DEPOSIT", operationAmount);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(updatedWallet.getBalance()));
    }

    @Test
    public void testPerformOperation_InsufficientFunds() throws Exception {
        UUID walletId = UUID.randomUUID();
        double initialBalance = 100.0;
        double operationAmount = 200.0;
        when(walletService.performOperation(walletId, "WITHDRAW", operationAmount))
                .thenThrow(new IllegalArgumentException("Insufficient funds for withdrawal."));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OperationDTO(walletId, "WITHDRAW", operationAmount))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Insufficient funds for withdrawal."));
    }

    @Test
    public void testGetWallet() throws Exception {
        UUID walletId = UUID.randomUUID();
        WalletDTO walletDTO = new WalletDTO(walletId, 1000.0);

        when(walletService.getWallet(walletId)).thenReturn(walletDTO);

        mockMvc.perform(get("/api/v1/wallets/{walletId}", walletId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(walletDTO.getBalance()));
    }

    @Test
    public void testPerformOperation_Withdraw_Success() throws Exception {
        UUID walletId = UUID.randomUUID();
        double initialBalance = 200.0;
        double operationAmount = 100.0;
        WalletDTO updatedWallet = new WalletDTO(walletId, initialBalance - operationAmount);

        when(walletService.performOperation(walletId, "WITHDRAW", operationAmount)).thenReturn(updatedWallet);

        OperationDTO operationDTO = new OperationDTO(walletId, "WITHDRAW", operationAmount);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(updatedWallet.getBalance()));
    }
}