package com.pingpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InvalidTransactionException extends RuntimeException{

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Trasaction not valid");
        pb.setDetail("This transaction is not valid. Please check the details and try again.");

        return pb;
    }
}
