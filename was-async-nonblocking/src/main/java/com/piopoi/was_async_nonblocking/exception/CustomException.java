package com.piopoi.was_async_nonblocking.exception;

import com.piopoi.was_async_nonblocking.core.HttpStatus;

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
