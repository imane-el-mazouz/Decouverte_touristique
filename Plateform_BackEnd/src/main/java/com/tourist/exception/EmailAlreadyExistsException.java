package com.tourist.exception;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException (Long id){
        super("Email with id already exists " + id);
    }
}
