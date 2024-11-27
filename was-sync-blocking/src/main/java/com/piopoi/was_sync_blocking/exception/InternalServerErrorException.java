package com.piopoi.was_sync_blocking.exception;

import static com.piopoi.was_sync_blocking.core.HttpStatus.INTERNAL_SERVER_ERROR;

import com.piopoi.was_sync_blocking.core.HttpStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InternalServerErrorException extends CustomException {
    private final HttpStatus httpStatus = INTERNAL_SERVER_ERROR;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public InternalServerErrorException(String message) {
        super(message);
        log.error(message);
    }

    public InternalServerErrorException(Throwable cause) {
        super(cause);
        log.error(cause.getMessage(), cause);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }
}
