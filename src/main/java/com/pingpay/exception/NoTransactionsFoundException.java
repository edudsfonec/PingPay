package com.pingpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class NoTransactionsFoundException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pb.setTitle("Transactions not found");
        pb.setDetail("There is no transactions registered in the system.");

        return pb;
    }
}
