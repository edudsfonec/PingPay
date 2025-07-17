package com.pingpay.controller;

import com.pingpay.config.SecurityConfig;
import com.pingpay.dto.LoginResponseDTO;
import com.pingpay.service.TokenService;
import com.pingpay.dto.WalletDTO;
import com.pingpay.model.Wallet;
import com.pingpay.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallets")
@Tag(name = "Wallet", description = "Endpoints for managing wallets")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class WalletController {

    private final WalletService walletService;
    private final TokenService tokenService;
    private AuthenticationManager authenticationManager;

    public WalletController(WalletService walletService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.walletService = walletService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login to wallet", description = "Authenticates a wallet user and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, token returned"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity login(@RequestBody WalletDTO wallet) {
        Authentication auth = authenticationManager.authenticate(walletService.authenticateLogin(wallet));

        String token = tokenService.generateToken((Wallet) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new wallet", description = "Creates a new wallet with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Wallet created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid wallet data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) {
        walletService.createWallet(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(wallet);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all wallets", description = "Retrieves a list of all wallets in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallets retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No wallets found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity getAllWallets() {
        return walletService.getAllWallets();
    }

    @GetMapping("/{email}")
@Operation(summary = "Get wallet by email", description = "Retrieves a wallet by its email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Wallet not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Wallet> getWallet(@PathVariable String email) {
        return walletService.getWallet(email);
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Delete wallet by email", description = "Deletes a wallet identified by its email address.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Wallet not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> deleteWallet(@PathVariable String email) {
        return walletService.deleteWallet(email);
    }

}
