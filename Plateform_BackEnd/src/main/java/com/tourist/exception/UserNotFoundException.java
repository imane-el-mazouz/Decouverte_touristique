package com.tourist.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String s){
        super("user not found !");
    }
}
