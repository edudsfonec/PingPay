package com.pingpay.handler;

import com.pingpay.exception.*;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WalletExceptionHandler {


    @ExceptionHandler(WalletNotFoundByEmailException.class)
    public ProblemDetail handlePicPayException(WalletNotFoundByEmailException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(WalletNotFoundByIdException.class)
    public ProblemDetail handlePicPayException(WalletNotFoundByIdException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(NoWalletsFoundException.class)
    public ProblemDetail handlePicPayException(NoWalletsFoundException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(PasswordNotValidException.class)
    public ProblemDetail handlePicPayException(PasswordNotValidException e) {
        return e.toProblemDetail();
    }

    @ExceptionHandler(WalletFieldNotValidException.class)
    public ProblemDetail handlePicPayException(WalletFieldNotValidException e) {
        return e.toProblemDetail();
    }
}
