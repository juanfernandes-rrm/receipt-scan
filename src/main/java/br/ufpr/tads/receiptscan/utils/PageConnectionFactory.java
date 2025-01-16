package br.ufpr.tads.receiptscan.utils;

import br.ufpr.tads.receiptscan.exception.InvalidReceiptException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class PageConnectionFactory {

    public HttpURLConnection getConnection(String URL){
        try {
            URL obj = new URL(URL);
            return (HttpURLConnection) obj.openConnection();
        } catch (MalformedURLException e) {
            throw new InvalidReceiptException("URL is invalid: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
