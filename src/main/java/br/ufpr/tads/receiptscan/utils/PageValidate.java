package br.ufpr.tads.receiptscan.utils;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

@Component
public class PageValidate {

    private static final String ERROR_INVALID_QRCODE = "#flashMessage > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > font";
    private static final String ERROR_MESSAGE = "103 - QR Code Inválido.";

    public void validatePage(Document document) {
        document.select(ERROR_INVALID_QRCODE)
                .stream()
                .findFirst()
                .map(element -> element.childNode(2).toString())
                .filter(ERROR_MESSAGE::equals)
                .ifPresent(error -> {
                    throw new RuntimeException("Invalid QR Code");
                });
    }

}
