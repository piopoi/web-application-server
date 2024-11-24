package com.piopoi.was_sync_blocking.core;

public class DispatcherServlet {

    public void dispatch(HttpRequest request, HttpResponse response) {
        response.writeBody("<h1>This is DispatcherServlet !!<h1>");
        response.flush();
    }
}
