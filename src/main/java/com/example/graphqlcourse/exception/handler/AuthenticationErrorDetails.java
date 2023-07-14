package com.example.graphqlcourse.exception.handler;

import com.netflix.graphql.types.errors.ErrorDetail;
import com.netflix.graphql.types.errors.ErrorType;

public class AuthenticationErrorDetails implements ErrorDetail {
    @Override
    public ErrorType getErrorType() {
        return ErrorType.UNAUTHENTICATED;
    }

    @Override
    public String toString() {
        return "User validation failed. Check that username and password combination match";
    }
}
