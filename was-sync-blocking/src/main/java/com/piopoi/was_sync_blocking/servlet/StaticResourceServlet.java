package com.piopoi.was_sync_blocking.servlet;

import static com.piopoi.was_sync_blocking.core.HttpServerConstants.CONTENT_TYPE_TEXT_HTML;
import static com.piopoi.was_sync_blocking.core.HttpStatus.OK;

import com.piopoi.was_sync_blocking.core.HttpRequest;
import com.piopoi.was_sync_blocking.core.HttpResponse;

public class StaticResourceServlet implements HttpServlet {
    private final String bodyContent;

    public StaticResourceServlet(String bodyContent) {
        this.bodyContent = bodyContent;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setHttpStatus(OK);
        response.setContentType(CONTENT_TYPE_TEXT_HTML);
        response.writeBody(bodyContent);
        response.flush();
    }
}