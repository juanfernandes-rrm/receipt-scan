package br.ufpr.tads.repceiptscan.model;

public enum PaymentMethod {

    CREDIT_CARD("CARTÃO DE CRÉDITO"),
    DEBIT_CARD("CARTÃO DE DÉBITO"),
    CASH("DINHEIRO"),
    PIX("PIX");

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
        throw new IllegalArgumentException("Unknown payment method: " + value);
    }

}
