package com.piopoi.was_sync_blocking.exception;

import static com.piopoi.was_sync_blocking.core.HttpStatus.*;

import com.piopoi.was_sync_blocking.core.HttpStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BadRequestException extends CustomException {
    private final HttpStatus httpStatus = BAD_REQUEST;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public BadRequestException(String message) {
        super(message);
        log.warn(message);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
        log.warn(cause.getMessage(), cause);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        log.warn(message, cause);
    }
}