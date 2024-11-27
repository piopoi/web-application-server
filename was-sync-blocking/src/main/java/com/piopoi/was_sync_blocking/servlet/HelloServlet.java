package com.piopoi.was_sync_blocking.servlet;

import com.piopoi.was_sync_blocking.core.HttpRequest;
import com.piopoi.was_sync_blocking.core.HttpResponse;

public class HelloServlet implements HttpServlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>This is a HelloServlet</h1>");
        response.flush();
    }
}
