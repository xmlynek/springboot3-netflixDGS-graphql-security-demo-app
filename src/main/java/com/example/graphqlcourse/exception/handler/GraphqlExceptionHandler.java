package com.example.graphqlcourse.exception.handler;

import com.example.graphqlcourse.exception.AuthenticationException;
import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class GraphqlExceptionHandler implements DataFetcherExceptionHandler {

    private final DefaultDataFetcherExceptionHandler defaultHandler = new DefaultDataFetcherExceptionHandler();
    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(
            DataFetcherExceptionHandlerParameters handlerParameters) {
        var exception = handlerParameters.getException();

        if (exception instanceof AuthenticationException) {
            var graphqlError = TypedGraphQLError.newBuilder()
                    .message(exception.getMessage())
                    .path(handlerParameters.getPath())
//                    .errorType(ErrorType.UNAUTHENTICATED)
                    .errorDetail(new AuthenticationErrorDetails())
                    .build();

            var result = DataFetcherExceptionHandlerResult.newResult()
                    .error(graphqlError).build();

            return CompletableFuture.completedFuture(result);
        }

        return defaultHandler.handleException(handlerParameters);
    }
}
