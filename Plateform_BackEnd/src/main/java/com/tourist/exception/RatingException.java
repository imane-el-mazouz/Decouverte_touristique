package com.tourist.exception;

public class RatingException extends RuntimeException{
    public RatingException(String msg){
        super("Rating must be between 1 and 5");
    }
}
