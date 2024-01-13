package br.ufpr.tads.repceiptscan.utils;

import org.springframework.stereotype.Component;

@Component
public class ReceiptURLValidate {

    private static final String URL_PATTERN = "http://www.fazenda.pr.gov.br/nfce/qrcode?p=";

    public void validate(String url) {
        if (!url.startsWith(URL_PATTERN)) {
            throw new RuntimeException("URL inválida");
        }
    }

}
