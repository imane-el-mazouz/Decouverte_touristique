package com.tourist.exception;

public class CardIsBlockedException extends RuntimeException{
    public CardIsBlockedException(){
        super("card blocked, please contact support !");
    }
}
