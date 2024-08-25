package com.tourist.exception;

public class TraditionAlreadyExistsException extends RuntimeException{
    public TraditionAlreadyExistsException(Long id){
        super("Tradition already exists " + id);
    }
}
