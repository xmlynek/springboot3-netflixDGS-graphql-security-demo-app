package com.example.graphqlcourse.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Invalid credentials");
    }

    public AuthenticationException(String message) {
        super(message);
    }
}
