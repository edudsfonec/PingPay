package com.pingpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WalletNotFoundByEmailException extends RuntimeException {
    private String email;

    public WalletNotFoundByEmailException(String email) {
        this.email = email;
    }

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Wallet not found");
        pb.setDetail("There is no wallet with email " + email + ".");

        return pb;
    }
}
