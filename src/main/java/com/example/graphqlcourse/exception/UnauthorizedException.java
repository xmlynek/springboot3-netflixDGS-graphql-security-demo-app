package com.example.graphqlcourse.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("You are not allowed to access this operation");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
