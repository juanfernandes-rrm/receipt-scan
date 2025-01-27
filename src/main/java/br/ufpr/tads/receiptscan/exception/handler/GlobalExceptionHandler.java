package br.ufpr.tads.receiptscan.exception.handler;

import br.ufpr.tads.receiptscan.exception.dto.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final RestHandlerExceptionResolver resolver;

    public GlobalExceptionHandler(RestHandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception exception) {
        return resolver.resolve(exception);
    }

}
