package br.ufpr.tads.receiptscan.model;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum PaymentMethod {

    CREDIT_CARD("CARTÃO DE CRÉDITO"),
    DEBIT_CARD("CARTÃO DE DÉBITO"),
    DEBIT_CARD_2("CARTÃO DE DÉBITO 2"),
    CASH("DINHEIRO"),
    PIX("PIX"),

    FOOD_VOUCHER("VALE ALIMENTAÇÃO"),
    OTHERS("OUTROS");

    private final String method;

    PaymentMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public static PaymentMethod fromValue(String value) {
        for (PaymentMethod paymentMethod : PaymentMethod.values()) {
            if (paymentMethod.getMethod().equalsIgnoreCase(value)) {
                return paymentMethod;
            }
        }
        log.warn("Payment method not found: {}", value);
        return OTHERS;
    }

}
