package com.tourist.exception;

public class BlogNotFoundException extends RuntimeException {
    public BlogNotFoundException (Long id ) {
        super("No id found for this blog " + id);
    }


}
