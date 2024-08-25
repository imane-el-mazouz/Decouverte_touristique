package com.tourist.exception;

public class EventAlreadyExistsException extends RuntimeException{
    public EventAlreadyExistsException (Long id){
        super("Event with id already exists " + id);
    }
}
