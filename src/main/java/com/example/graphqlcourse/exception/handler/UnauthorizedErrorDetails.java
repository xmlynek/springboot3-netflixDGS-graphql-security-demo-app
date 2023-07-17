package com.example.graphqlcourse.exception.handler;

import com.netflix.graphql.types.errors.ErrorDetail;
import com.netflix.graphql.types.errors.ErrorType;

public class UnauthorizedErrorDetails implements ErrorDetail {
    @Override
    public ErrorType getErrorType() {
        return ErrorType.PERMISSION_DENIED;
    }

    @Override
    public String toString() {
        return "You are not allowed to access this operation";
    }
}
