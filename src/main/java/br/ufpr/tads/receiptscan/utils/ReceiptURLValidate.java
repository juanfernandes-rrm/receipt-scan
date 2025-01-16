package br.ufpr.tads.receiptscan.utils;

import br.ufpr.tads.receiptscan.exception.InvalidReceiptException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReceiptURLValidate {

    private static final String URL_PATTERN = "http://www.fazenda.pr.gov.br/nfce/qrcode?p=";

    public void validate(String url) {
        if (!url.startsWith(URL_PATTERN)) {
            log.error("Invalid URL: {}", url);
            throw new InvalidReceiptException(String.format("URL %s is invalid", url));
        }
    }

}
