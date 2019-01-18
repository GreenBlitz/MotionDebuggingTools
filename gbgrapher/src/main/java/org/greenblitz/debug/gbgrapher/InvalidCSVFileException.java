package org.greenblitz.debug.gbgrapher;

import java.io.IOException;

public class InvalidCSVFileException extends Exception {
    public InvalidCSVFileException() {
        super();
    }

    public InvalidCSVFileException(String message) {
        super(message);
    }

    public InvalidCSVFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCSVFileException(Throwable cause) {
        super(cause);
    }

    protected InvalidCSVFileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
