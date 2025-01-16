package br.ufpr.tads.receiptscan.exception;

public class InvalidReceiptException extends RuntimeException {

    public InvalidReceiptException(String s) {
        super(s);
    }

    public InvalidReceiptException(String message, Throwable cause) {
        super(message, cause);
    }
}
