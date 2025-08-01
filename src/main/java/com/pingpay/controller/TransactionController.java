package com.pingpay.controller;


import com.pingpay.config.SecurityConfig;
import com.pingpay.dto.TransactionRequestDTO;
import com.pingpay.dto.TransactionsResponseDTO;
import com.pingpay.model.Transaction;
import com.pingpay.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/transactions")
@Tag(name = "Transaction", description = "Endpoints for managing transactions")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @Operation(summary = "Create a new transaction", description = "Creates a new transaction between two wallets. The payer and payee must exist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid transaction request"),
            @ApiResponse(responseCode = "404", description = "Payer or payee wallet not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User does not have permission to perform this action")
    })
    public Transaction createTransaction(@RequestBody TransactionRequestDTO transaction) {
        return transactionService.create(transaction);
    }


    @GetMapping("/history/{email}")
    @Operation(summary = "Get transaction history by email", description = "Retrieves the transaction history for a specific wallet identified by email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction history retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No transactions found for the given email"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User does not have permission to perform this action")

    })
    public List<TransactionsResponseDTO> findTransactionsByEmail(@PathVariable("email") String email) {
        return transactionService.history(email);
    }

    @GetMapping
    @Operation(summary = "Get all transactions", description = "Retrieves a list of all transactions in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No transactions found"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User does not have permission to perform this action"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<TransactionsResponseDTO> getAllTransactions() {
        return transactionService.listAll();
    }

}
