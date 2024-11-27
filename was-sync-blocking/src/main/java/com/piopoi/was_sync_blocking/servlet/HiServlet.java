package com.piopoi.was_sync_blocking.servlet;

import com.piopoi.was_sync_blocking.core.HttpRequest;
import com.piopoi.was_sync_blocking.core.HttpResponse;

public class HiServlet implements HttpServlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>This is a HiServlet</h1>");
        response.flush();
    }
}
