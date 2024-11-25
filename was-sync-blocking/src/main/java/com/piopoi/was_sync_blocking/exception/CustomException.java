package com.piopoi.was_sync_blocking.exception;

import com.piopoi.was_sync_blocking.core.HttpStatus;

public abstract class CustomException extends RuntimeException {

    public abstract HttpStatus getHttpStatus();

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }
}
