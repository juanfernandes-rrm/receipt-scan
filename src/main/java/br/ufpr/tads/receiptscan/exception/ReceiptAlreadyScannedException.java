package br.ufpr.tads.receiptscan.exception;

public class ReceiptAlreadyScannedException extends RuntimeException {
    public ReceiptAlreadyScannedException(String s) {
        super(s);
    }
}
