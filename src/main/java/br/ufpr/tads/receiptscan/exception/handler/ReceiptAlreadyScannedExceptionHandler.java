package br.ufpr.tads.receiptscan.exception.handler;

import br.ufpr.tads.receiptscan.exception.ReceiptAlreadyScannedException;
import br.ufpr.tads.receiptscan.exception.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ReceiptAlreadyScannedExceptionHandler implements RestExceptionHandler<ReceiptAlreadyScannedException> {

    @Override
    public ResponseEntity<ApiError> handleException(ReceiptAlreadyScannedException exception) {
        ApiError error = ApiError.builder()
                .type("https://tads.ufpr.br/receiptscan/receipt-already-scanned")
                .title("Receipt already scanned")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(exception.getMessage()).build();
        return ResponseEntity.badRequest().body(error);
    }

    @Override
    public Class<ReceiptAlreadyScannedException> getExceptionType() {
        return ReceiptAlreadyScannedException.class;
    }
}
