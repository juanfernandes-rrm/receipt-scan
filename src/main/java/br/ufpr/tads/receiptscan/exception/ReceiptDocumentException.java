package br.ufpr.tads.receiptscan.exception;

public class ReceiptDocumentException extends RuntimeException {

    public ReceiptDocumentException(String s) {
        super(s);
    }

    public ReceiptDocumentException(String message, Throwable cause) {
        super(message, cause);
    }

}
