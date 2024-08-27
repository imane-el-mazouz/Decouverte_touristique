package com.tourist.exception;

public class ReviewNotFound extends RuntimeException{
    public ReviewNotFound(String id){
        super("review not found " + id);
    }

}
