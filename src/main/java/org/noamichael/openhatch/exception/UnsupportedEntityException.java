package org.noamichael.openhatch.exception;

public class UnsupportedEntityException extends RuntimeException {

    public UnsupportedEntityException() {
    }

    public UnsupportedEntityException(String message) {
        super(message);
    }

    public UnsupportedEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedEntityException(Throwable cause) {
        super(cause);
    }

    public UnsupportedEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
