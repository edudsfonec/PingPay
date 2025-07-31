package com.pingpay.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class PasswordNotValidException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Password is not valid or secure enough");
        pb.setDetail("Password is not valid. Please check if the value is correct. " +
                     "The password must be at least 8 characters long, " +
                     "contain at least one uppercase letter, " +
                     "one lowercase letter, one digit, and one special character.");

        return pb;
    }
}
