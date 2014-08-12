package org.noamichael.openhatch.exception;

public class UnsatisfiedEMException extends RuntimeException {

    public UnsatisfiedEMException() {
    }

    public UnsatisfiedEMException(String message) {
        super(message);
    }

    public UnsatisfiedEMException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsatisfiedEMException(Throwable cause) {
        super(cause);
    }

    public UnsatisfiedEMException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
