package com.pingpay.handler;

import com.pingpay.exception.InvalidTransactionException;
import com.pingpay.exception.NoTransactionsFoundException;
import com.pingpay.exception.TransactionsNotFoundByWalletException;
import com.pingpay.exception.WalletNotFoundByEmailException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler(InvalidTransactionException.class)
    public ProblemDetail handlePicPayException(InvalidTransactionException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(TransactionsNotFoundByWalletException.class)
    public ProblemDetail handlePicPayException(TransactionsNotFoundByWalletException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(NoTransactionsFoundException.class)
    public ProblemDetail handlePicPayException(NoTransactionsFoundException e) {
        return e.toProblemDetail();
    }
}
