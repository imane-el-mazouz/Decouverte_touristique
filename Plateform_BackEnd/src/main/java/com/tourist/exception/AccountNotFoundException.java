package com.tourist.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String toAccountNotFound){
        super("account not found !");
    }
}
