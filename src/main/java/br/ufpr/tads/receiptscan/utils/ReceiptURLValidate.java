package br.ufpr.tads.receiptscan.utils;

import br.ufpr.tads.receiptscan.exception.InvalidReceiptException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReceiptURLValidate {

    @Value("${receipt-scan.nfce-url-base}")
    private String nfceUrlBase;

    public void validate(String url) {
        if (!url.startsWith(nfceUrlBase)) {
            log.error("Invalid URL: {}", url);
            throw new InvalidReceiptException(String.format("URL %s is invalid", url));
        }
    }

}
