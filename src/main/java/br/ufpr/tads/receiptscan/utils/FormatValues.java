package br.ufpr.tads.receiptscan.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class FormatValues {

    private static final String NON_BREAKING_SPACE = "&nbsp;";

    public BigDecimal formatDecimalValue(String value) {
        try {
            value = value.trim()
                    .replace(NON_BREAKING_SPACE, "")
                    .replace(".", "")
                    .replace(",", ".");

            if(isBlank(value)) {
                return BigDecimal.ZERO;
            }

            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter valor monetário: " + e.getMessage());
        }
        return null;
    }

    public LocalDateTime formatDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }

}
