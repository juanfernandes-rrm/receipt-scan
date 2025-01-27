package br.ufpr.tads.receiptscan.exception.handler;

import br.ufpr.tads.receiptscan.exception.dto.ApiError;
import org.springframework.http.ResponseEntity;

public interface RestExceptionHandler<E extends Exception> {
    ResponseEntity<ApiError> handleException(E exception);
    Class<E> getExceptionType();
}

