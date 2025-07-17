package com.pingpay.dto;

import java.math.BigDecimal;

public class TransactionRequestDTO {

    private Long payer;

    private Long payee;

    private BigDecimal price;

    public TransactionRequestDTO(Long payer, Long payeeName, BigDecimal price) {
        this.payer = payer;
        this.payee = payeeName;
        this.price = price;
    }

    public Long getPayer() {
        return payer;
    }

    public void setPayer(Long payer) {
        this.payer = payer;
    }

    public Long getPayee() {
        return payee;
    }

    public void setPayee(Long payee) {
        this.payee = payee;
    }

    public BigDecimal getValue() {
        return price;
    }

    public void setValue(BigDecimal price) {
        this.price = price;
    }
}
