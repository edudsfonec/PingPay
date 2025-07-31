package com.pingpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class WalletFieldNotValidException extends RuntimeException {
    private String field;

    public WalletFieldNotValidException(String field) {
        this.field = field;
    }

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle(field + " not valid");
        pb.setDetail(field + " is not valid. Please check if the value is correct.");

        return pb;
    }
}
