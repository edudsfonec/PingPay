package com.pingpay.model;

import com.pingpay.enuns.WalletType;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Table(name = "WALLETS")
@Entity(name = "WALLETS")
public class Wallet implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "cpf", unique = true, nullable = false)
    private Long cpf;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "type", nullable = false)
    private int type;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    public Wallet(Long id, String fullName, Long cpf, String email, String password, int type, BigDecimal subtract) {
    }

    public Wallet() {

    }

    public Wallet debit(BigDecimal value) {
        return new Wallet(
                this.id,
                this.fullName,
                this.cpf,
                this.email,
                this.password,
                this.type,
                this.balance.subtract(value)
        );
    }

    public Wallet credit(BigDecimal value) {
        return new Wallet(
                this.id,
                this.fullName,
                this.cpf,
                this.email,
                this.password,
                this.type,
                this.balance.add(value)
        );
    }

    public Wallet saveWithPasswordPlusBalance(String encodedPassword) {
        return new Wallet(
                this.id,
                this.fullName,
                this.cpf,
                this.email,
                encodedPassword,
                this.type,
                this.balance.add(BigDecimal.valueOf(10))
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.type == WalletType.ADMIN.getValue())
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
//        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
//        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
//        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return true;
//        return UserDetails.super.isEnabled();
    }
}
