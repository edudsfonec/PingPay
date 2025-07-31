package com.pingpay.service;

import com.pingpay.dto.TransactionRequestDTO;
import com.pingpay.dto.TransactionsResponseDTO;
import com.pingpay.exception.*;
import com.pingpay.mapper.TransactionMapper;
import com.pingpay.model.Transaction;
import com.pingpay.model.Wallet;
import com.pingpay.repository.TransactionRepository;
import com.pingpay.repository.WalletRepository;
import com.pingpay.enuns.WalletType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository TransactionRepository, WalletRepository walletRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = TransactionRepository;
        this.walletRepository = walletRepository;
        this.transactionMapper = transactionMapper;
   }

    @Transactional
    public Transaction create(TransactionRequestDTO transaction) {
        Wallet walletPayer = walletRepository.findById(transaction.getPayer())
                .orElseThrow(() -> new WalletNotFoundByIdException(transaction.getPayer()));
        Wallet walletPayee = walletRepository.findById(transaction.getPayee())
                .orElseThrow(() -> new WalletNotFoundByIdException(transaction.getPayee()));

        try {
            isTransactionValid(transaction, walletPayer);
        } catch(InvalidTransactionException e) {
            throw new InvalidTransactionException();
        }

        Transaction newTransaction = transactionMapper.mapToEntity(transaction);

        transactionRepository.save(newTransaction);

        walletPayer.debit(transaction.getValue());
        walletPayee.credit(transaction.getValue());

        return newTransaction;
    }

    public List<TransactionsResponseDTO> listAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        if (transactions.isEmpty()) {
            throw new NoTransactionsFoundException();
        }
        return transactions.stream()
                .map(transaction -> {
                    TransactionsResponseDTO dto = transactionMapper.mapToResponse(transaction);

                    String payerName = walletRepository.findById(transaction.getPayer())
                            .map(Wallet::getFullName)
                            .orElse("Unknown Payer");
                    dto.setPayer(payerName);

                    String payeeName = walletRepository.findById(transaction.getPayee())
                            .map(Wallet::getFullName)
                            .orElse("Unknown Payee");
                    dto.setPayee(payeeName);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<TransactionsResponseDTO> history(String email) {
        Wallet wallet = walletRepository
                .findByEmail(email)
                .orElseThrow(() -> new WalletNotFoundByEmailException(email));
        List<Transaction> transactions = transactionRepository.findTransactionsByPayer(wallet.getId());

        if (transactions.isEmpty()) {
            throw new TransactionsNotFoundByWalletException();
        }
        return transactions.stream()
                .map(transaction -> {
                    String payerName = wallet.getFullName();

                    String payeeName = walletRepository
                            .findById(transaction.getPayee())
                            .map(Wallet::getFullName)
                            .orElse("Unknown Payee");

                    return new TransactionsResponseDTO(
                            payerName,
                            payeeName,
                            transaction.getValue(),
                            transaction.getCreatedAt()
                    );
                }).collect(Collectors.toList());
    }

//    private void validate(TransactionRequestDTO transaction) {
//        walletRepository.findById(transaction.getPayee())
//            .map(payee -> walletRepository.findById(transaction.getPayer())
//            .map(payer -> isTransactionValid(transaction, payer) ? transaction : null)
//            .orElseThrow(() -> new InvalidTransactionException("Invalid transaction - %s".formatted(transaction))))
//        .orElseThrow(() -> new InvalidTransactionException("Invalid transaction - %s".formatted(transaction)));
//    }

    private boolean isTransactionValid(TransactionRequestDTO transaction, Wallet payer) {
        return payer.getType() != WalletType.PJ.getValue() &&
                payer.getBalance().compareTo(transaction.getValue()) >= 0 &&
                !payer.getId().equals(transaction.getPayee());
    }



}
