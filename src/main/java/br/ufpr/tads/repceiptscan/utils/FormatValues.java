package br.ufpr.tads.repceiptscan.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatValues {

    private static final String NON_BREAKING_SPACE = "&nbsp;";

    public BigDecimal formatDecimalValue(String value) {
        try {
            value = value.trim()
                    .replace(NON_BREAKING_SPACE, "")
                    .replace(".", "")
                    .replace(",", ".");
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
