package com.tourist.exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException (Long id){
        super("Event with id already exists " + id);
    }
}
