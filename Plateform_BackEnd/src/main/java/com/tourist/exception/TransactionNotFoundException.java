package com.tourist.exception;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(){
        super("transaction not found !");
    }
}
