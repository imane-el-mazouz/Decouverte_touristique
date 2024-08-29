package com.tourist.exception;

public class EventAlreadyExistsException extends RuntimeException{
    public EventAlreadyExistsException (String id){
        super("Event with id already exists " + id);
    }
}
