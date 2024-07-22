package com.tourist.exception;

public class DatabaseEmptyException extends RuntimeException{
    public DatabaseEmptyException(){
        super("database empty, no data found !");
    }
}
