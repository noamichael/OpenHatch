package org.noamichael.openhatch.exception;

public class AccountNotAddedException extends RuntimeException {

    public AccountNotAddedException() {
    }

    public AccountNotAddedException(String message) {
        super(message);
    }

    public AccountNotAddedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotAddedException(Throwable cause) {
        super(cause);
    }

    public AccountNotAddedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
