package com.tourist.exception;

public class ExcursionAlreadyExistsException extends RuntimeException{
    public ExcursionAlreadyExistsException (Long id){
        super("Excursion with id already exists " + id);
    }
}
