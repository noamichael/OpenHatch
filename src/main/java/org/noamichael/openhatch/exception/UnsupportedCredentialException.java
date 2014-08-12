package org.noamichael.openhatch.exception;

public class UnsupportedCredentialException extends RuntimeException {

    public UnsupportedCredentialException() {
    }

    public UnsupportedCredentialException(String message) {
        super(message);
    }

    public UnsupportedCredentialException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedCredentialException(Throwable cause) {
        super(cause);
    }

    public UnsupportedCredentialException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
