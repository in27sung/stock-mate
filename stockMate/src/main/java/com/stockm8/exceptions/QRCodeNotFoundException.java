package com.stockm8.exceptions;

public class QRCodeNotFoundException extends RuntimeException {

    public QRCodeNotFoundException(String message) {
        super(message);
    }

    public QRCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}