package br.ufpr.tads.receiptscan.exception.handler;

import br.ufpr.tads.receiptscan.exception.ReceiptDocumentException;
import br.ufpr.tads.receiptscan.exception.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ReceiptDocumentExceptionHandler implements RestExceptionHandler<ReceiptDocumentException>{

    @Override
    public ResponseEntity<ApiError> handleException(ReceiptDocumentException exception) {
        ApiError error = ApiError.builder()
                .type("https://tads.ufpr.br/receiptscan/reicept-document-error")
                .title("Receipt document error")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(exception.getMessage()).build();
        return ResponseEntity.badRequest().body(error);
    }

    @Override
    public Class<ReceiptDocumentException> getExceptionType() {
        return ReceiptDocumentException.class;
    }

}
