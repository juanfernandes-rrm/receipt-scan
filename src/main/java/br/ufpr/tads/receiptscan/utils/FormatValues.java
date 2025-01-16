package br.ufpr.tads.receiptscan.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Slf4j
public class FormatValues {

    private static final String NON_BREAKING_SPACE = "&nbsp;";

    public static BigDecimal formatDecimalValue(String value) {
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
            log.error("Error converting monetary value {}: {}", value, e.getMessage());
        }
        return null;
    }

    public static LocalDateTime formatDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyyHH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }

}
