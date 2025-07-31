package com.pingpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class NoWalletsFoundException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pb.setTitle("Wallets not found");
        pb.setDetail("There is no wallets registered in the system.");

        return pb;
    }
}
