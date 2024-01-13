package br.ufpr.tads.repceiptscan.utils;

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
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            System.out.println("Response code: " + con.getResponseCode());
            return con;
        } catch (MalformedURLException e) {
            System.out.println("URL inválida: "+e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
