package com.pingpay.service;

import com.pingpay.dto.WalletLoginDTO;
import com.pingpay.dto.WalletRequestDTO;
import com.pingpay.dto.WalletResponseDTO;
import com.pingpay.exception.NoWalletsFoundException;
import com.pingpay.exception.PasswordNotValidException;
import com.pingpay.exception.WalletFieldNotValidException;
import com.pingpay.exception.WalletNotFoundByEmailException;
import com.pingpay.mapper.WalletMapper;
import com.pingpay.model.Wallet;
import com.pingpay.repository.WalletRepository;
import org.springframework.http.HttpStatus;
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
    private final WalletMapper walletMapper;

    public WalletService(WalletRepository walletRepository, WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return walletRepository.findByEmail(username).orElseThrow();
    }

    public UsernamePasswordAuthenticationToken authenticateLogin(WalletLoginDTO walletLoginDTO) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(walletLoginDTO.getEmail(), walletLoginDTO.getPassword());
        return token;
    }

    @Transactional
    public WalletResponseDTO createWallet(WalletRequestDTO walletRequestDTO) {
        validateUniqueFields(walletRequestDTO);
        validatePassword(walletRequestDTO.getPassword());

        Wallet wallet = walletMapper.mapToWallet(walletRequestDTO);

        String encodedPassword = new BCryptPasswordEncoder().encode(wallet.getPassword());
        wallet.setPassword(encodedPassword);
        walletRepository.save(wallet);
        return walletMapper.mapToResponse(wallet);
    }

    public WalletResponseDTO getWallet(String email) {
        Wallet wallet = walletRepository.findByEmail(email)
                .orElseThrow(() -> new WalletNotFoundByEmailException(email));
        return walletMapper.mapToResponse(wallet);

    }

    public void deleteWallet(String email) {
        Wallet wallet = walletRepository.findByEmail(email)
                .orElseThrow(()-> new WalletNotFoundByEmailException(email));
        walletRepository.delete(wallet);
    }

    public List<WalletResponseDTO> getAllWallets() {
        List<Wallet> wallets = walletRepository.findAll();
        if (wallets.isEmpty()) {
            throw new NoWalletsFoundException();
        }
        return walletMapper.mapToResponseList(wallets);
    }

    private void validateUniqueFields(WalletRequestDTO newWallet) {
        Optional<Wallet> existingWalletByCpf = walletRepository.findByCpf(newWallet.getCpf());
        if (existingWalletByCpf.isPresent()) {
            throw new WalletFieldNotValidException("CPF or CNPJ");
        }
//        ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "CPF already registered for another wallet.")

        Optional<Wallet> existingWalletByEmail = walletRepository.findByEmail(newWallet.getEmail());
        if (existingWalletByEmail.isPresent()) {
            throw new WalletFieldNotValidException("Email");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new PasswordNotValidException();
        }

        String passwordRegex = "^(?=.*\\d).{6,}$";
        Pattern pattern = Pattern.compile(passwordRegex);

        if(!pattern.matcher(password).matches()){
            throw new PasswordNotValidException();
        }
    }

}
