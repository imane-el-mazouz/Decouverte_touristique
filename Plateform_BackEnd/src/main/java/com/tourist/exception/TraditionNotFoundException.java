package com.tourist.exception;

public class TraditionNotFoundException extends RuntimeException{
    public TraditionNotFoundException( Long id){
        super("tradition not found : " + id);
    }
}
