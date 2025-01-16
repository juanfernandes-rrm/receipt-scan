package br.ufpr.tads.receiptscan.utils;

import br.ufpr.tads.receiptscan.exception.InvalidReceiptException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PageValidate {

    private static final String ERROR_INVALID_QRCODE = "#flashMessage > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > font";
    private static final String ERROR_MESSAGE = "100 - QR Code Inválido.";

    public void validatePage(Document document, String url) {
        document.select(ERROR_INVALID_QRCODE)
                .stream()
                .findFirst()
                .map(element -> element.childNode(2).toString())
                .filter(ERROR_MESSAGE::equals)
                .ifPresent(error -> {
                    log.error("Invalid QR Code: {}", url);
                    throw new InvalidReceiptException("Invalid QR Code");
                });
    }

}
