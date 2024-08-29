package com.tourist.exception;

public class HotelNotFoundException extends RuntimeException{
    public HotelNotFoundException(Long id){
        super("no hotel found with this id" + id);
    }
}
