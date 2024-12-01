package br.ufpr.tads.receiptscan.service;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AccessKeyExtractor {

    public static final String REGEX_TO_EXTRACT_ACCESS_KEY_FROM_URL = "(?<=p=)\\d{44}(?=\\|)";

    public String extract(String url) {
        Pattern pattern = Pattern.compile(REGEX_TO_EXTRACT_ACCESS_KEY_FROM_URL);
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group() : null;
    }

}
