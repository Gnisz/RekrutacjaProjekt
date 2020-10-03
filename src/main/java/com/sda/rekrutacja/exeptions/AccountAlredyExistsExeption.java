package com.sda.rekrutacja.exeptions;

public class AccountAlredyExistsExeption extends RuntimeException {

    public AccountAlredyExistsExeption(String message) {
        super(message);
    }
}
