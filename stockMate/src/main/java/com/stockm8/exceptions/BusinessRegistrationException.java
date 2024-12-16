package com.stockm8.exceptions;

public class BusinessRegistrationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessRegistrationException() {
        super();
    }

    public BusinessRegistrationException(String message) {
        super(message);
    }

    public BusinessRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessRegistrationException(Throwable cause) {
        super(cause);
    }
}
