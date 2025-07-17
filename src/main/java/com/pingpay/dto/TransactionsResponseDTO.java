package com.pingpay.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionsResponseDTO {

    private String payer;

    private String payee;

    private BigDecimal price;

    private LocalDateTime createdAt;


    public TransactionsResponseDTO(String payer, String payee, BigDecimal price, LocalDateTime createdAt) {
        this.payer = payer;
        this.payee = payee;
        this.price = price.setScale(2);
        this.createdAt = createdAt;
    }

    public TransactionsResponseDTO() {
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public BigDecimal getValue() {
        return price;
    }

    public void setValue(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
