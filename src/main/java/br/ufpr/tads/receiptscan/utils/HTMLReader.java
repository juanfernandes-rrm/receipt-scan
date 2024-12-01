package br.ufpr.tads.receiptscan.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

@Component
public class HTMLReader {


    public Document getDocument(HttpURLConnection connection) {
        String html;
        try {
            html = getHTML(connection.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Jsoup.parse(html);
    }

    private String getHTML(InputStream inputStream) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
