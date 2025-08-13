package br.ufpr.tads.receiptscan.exception.handler;

import br.ufpr.tads.receiptscan.exception.ScrapingFailedException;
import br.ufpr.tads.receiptscan.exception.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ScrapingFailedExceptionHandler implements RestExceptionHandler<ScrapingFailedException>{

    @Override
    public ResponseEntity<ApiError> handleException(ScrapingFailedException exception) {
        ApiError error = ApiError.builder()
                .type("https://tads.ufpr.br/receiptscan/scraping-failed")
                .title("Scraping Failed")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(exception.getMessage()).build();
        return ResponseEntity.badRequest().body(error);
    }

    @Override
    public Class<ScrapingFailedException> getExceptionType() {
        return ScrapingFailedException.class;
    }
}
