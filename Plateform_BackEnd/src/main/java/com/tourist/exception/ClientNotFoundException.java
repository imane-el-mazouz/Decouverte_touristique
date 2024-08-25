package com.tourist.exception;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(Long id) {
        super(" user not found :" + id);
    }
}
