package com.pingpay.dto;

public class WalletRequestDTO {

    private String fullName;
    private Long cpf;
    private String email;
    private String password;
    private String balance;

    public WalletRequestDTO(String fullName, Long cpf, String email, String password, String balance) {
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}

