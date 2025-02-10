package com.piopoi.was_sync_nonblocking.exception;

import static com.piopoi.was_sync_nonblocking.core.HttpStatus.INTERNAL_SERVER_ERROR;

import com.piopoi.was_sync_nonblocking.core.HttpRequest;
import com.piopoi.was_sync_nonblocking.core.HttpResponse;
import com.piopoi.was_sync_nonblocking.core.HttpStatus;
import com.piopoi.was_sync_nonblocking.servlet.ErrorServlet;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionHandler {
    private final HttpStatus defaultHttpStatus = INTERNAL_SERVER_ERROR;

    public void handleException(Exception e, HttpRequest request, HttpResponse response) {
        HttpStatus httpStatus = getHttpStatus(e);
        ErrorServlet errorServlet = new ErrorServlet(httpStatus);
        errorServlet.service(request, response);
    }

    private HttpStatus getHttpStatus(Exception e) {
        log.error(e.getMessage(), e);
        if (e instanceof CustomException) {
            return ((CustomException) e).getHttpStatus();
        } else {
            return defaultHttpStatus;
        }
    }
}
