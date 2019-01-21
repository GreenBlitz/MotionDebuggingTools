package org.greenblitz.debug.csvlogger.common.exception;

public class DirectoryCreationException extends Exception {
    public DirectoryCreationException() {
    }

    public DirectoryCreationException(String message) {
        super(message);
    }

    public DirectoryCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DirectoryCreationException(Throwable cause) {
        super(cause);
    }

    public DirectoryCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
