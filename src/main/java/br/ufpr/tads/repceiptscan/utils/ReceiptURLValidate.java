package br.ufpr.tads.repceiptscan.utils;

import br.ufpr.tads.repceiptscan.dto.ReceiptRequestDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.util.Objects.nonNull;

@Component
public class ReceiptURLValidate {

    private static final String URL_PATTERN = "http://www.fazenda.pr.gov.br/nfce/qrcode?p=";

    public void validate(ReceiptRequestDTO receiptRequestDTO) {
        if (nonNull(receiptRequestDTO)) {
            String url = receiptRequestDTO.getUrl();
            validatePattern(url);
            validateConnection(url);
        }
    }

    private void validatePattern(String url){
        if (!url.startsWith(URL_PATTERN)) {
            throw new RuntimeException("URL inválida");
        }
    }

    private void validateConnection(String url){
        HttpURLConnection connection;
        int responseCode;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            responseCode = connection.getResponseCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Código de resposta HTTP não OK: " + responseCode);
        }
    }

}
