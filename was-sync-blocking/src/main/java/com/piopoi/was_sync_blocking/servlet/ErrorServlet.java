package com.piopoi.was_sync_blocking.servlet;

import com.piopoi.was_sync_blocking.core.HttpRequest;
import com.piopoi.was_sync_blocking.core.HttpResponse;
import com.piopoi.was_sync_blocking.core.HttpStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorServlet implements HttpServlet {
    private final HttpStatus httpStatus;

    public ErrorServlet(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.clearHeadersAndBody();
        response.setHttpStatus(httpStatus);
        response.writeBody(httpStatus.getDefaultResponseBody());
        response.flush();
    }
}
