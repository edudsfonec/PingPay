package com.pingpay.service;

import com.pingpay.dto.WalletDTO;
import com.pingpay.model.Wallet;
import com.pingpay.repository.WalletRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class WalletService implements UserDetailsService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return walletRepository.FindByEmail(username);
    }

    public UsernamePasswordAuthenticationToken authenticateLogin(WalletDTO walletDTO) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(walletDTO.getEmail(), walletDTO.getPassword());
        return token;
    }

    @Transactional
    public void createWallet(Wallet wallet) {
        validateUniqueFields(wallet);

        validatePassword(wallet.getPassword());
        String encodedPassword = new BCryptPasswordEncoder().encode(wallet.getPassword());
        wallet.setPassword(encodedPassword);
        walletRepository.save(wallet);
    }

    public ResponseEntity<Wallet> getWallet(String email) {
        return walletRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found."));
    }

    public ResponseEntity deleteWallet(String email) {
        return walletRepository.findByEmail(email)
                .map(wallet -> {
                    walletRepository.delete(wallet);
                    return ResponseEntity.ok("Wallet deleted successfully.");
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found."));
    }

    public ResponseEntity getAllWallets() {
        List<Wallet> wallets = walletRepository.findAll();
        if (wallets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No wallets found.");
        }
        return ResponseEntity.ok(wallets);
    }

    private void validateUniqueFields(Wallet newWallet) {
        Optional<Wallet> existingWalletByCpf = walletRepository.findByCpf(newWallet.getCpf());
        if (existingWalletByCpf.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "CPF already registered for another wallet.");
        }

        Optional<Wallet> existingWalletByEmail = walletRepository.findByEmail(newWallet.getEmail());
        if (existingWalletByEmail.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email already registered for another wallet.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password cannot be empty.");
        }

        String passwordRegex = "^(?=.*\\d).{6,}$";
        Pattern pattern = Pattern.compile(passwordRegex);

        if(!pattern.matcher(password).matches()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Password is not valid.");
        }
    }

}
