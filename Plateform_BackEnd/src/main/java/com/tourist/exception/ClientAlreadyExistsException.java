package com.tourist.exception;

public class ClientAlreadyExistsException extends RuntimeException {
    public ClientAlreadyExistsException(Long id ){
        super("user already exists :" + id);
    }

}
