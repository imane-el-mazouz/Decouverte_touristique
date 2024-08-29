package com.tourist.exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException (String id){
        super("Event with id already exists " + id);
    }
}
