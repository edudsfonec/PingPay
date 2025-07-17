package com.pingpay.enuns;

public enum WalletType {
    ADMIN(0),
    PF(1),
    PJ(2);

    private int value;

    private WalletType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
