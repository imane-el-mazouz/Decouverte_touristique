package com.tourist.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(){
        super("insufficient balance !");
    }
}
