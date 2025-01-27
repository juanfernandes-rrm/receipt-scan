package br.ufpr.tads.receiptscan.exception.handler;

import br.ufpr.tads.receiptscan.exception.InvalidReceiptException;
import br.ufpr.tads.receiptscan.exception.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class InvalidReceiptExceptionHandler implements RestExceptionHandler<InvalidReceiptException> {

    @Override
    public ResponseEntity<ApiError> handleException(InvalidReceiptException exception) {
        ApiError error = ApiError.builder()
                .type("https://tads.ufpr.br/receiptscan/invalid-receipt")
                .title("Invalid receipt")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(exception.getMessage()).build();
        return ResponseEntity.badRequest().body(error);
    }

    @Override
    public Class<InvalidReceiptException> getExceptionType() {
        return InvalidReceiptException.class;
    }
}
