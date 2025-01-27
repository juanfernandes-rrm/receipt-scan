package br.ufpr.tads.receiptscan.exception.handler;

import br.ufpr.tads.receiptscan.exception.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestHandlerExceptionResolver {

    private final Map<Class<? extends Exception>, RestExceptionHandler<?>> handlers = new HashMap<>();

    public RestHandlerExceptionResolver(List<RestExceptionHandler<?>> handlerList) {
        for (RestExceptionHandler<?> handler : handlerList) {
            handlers.put(handler.getExceptionType(), handler);
        }
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<ApiError> resolve(Exception exception) {
        Class<?> exceptionClass = exception.getClass();

        while (exceptionClass != null) {
            RestExceptionHandler<?> handler = handlers.get(exceptionClass);
            if (handler != null) {
                return ((RestExceptionHandler<Exception>) handler).handleException(exception);
            }
            exceptionClass = exceptionClass.getSuperclass();
        }

        ApiError internalServerError = ApiError.builder()
                .type("https://tads.ufpr.br/receiptscan/internal-server-error")
                .title("Internal server error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detail(exception.getMessage()).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(internalServerError);
    }

}

