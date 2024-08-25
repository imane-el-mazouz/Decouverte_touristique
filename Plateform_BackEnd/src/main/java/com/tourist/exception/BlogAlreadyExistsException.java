package com.tourist.exception;

public class BlogAlreadyExistsException extends RuntimeException{
    public BlogAlreadyExistsException (Long id){
        super("Blog with id already exists " + id);
    }
}
