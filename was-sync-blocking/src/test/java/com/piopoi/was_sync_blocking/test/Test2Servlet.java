package com.piopoi.was_sync_blocking.test;

import com.piopoi.was_sync_blocking.core.HttpRequest;
import com.piopoi.was_sync_blocking.core.HttpResponse;
import com.piopoi.was_sync_blocking.servlet.HttpServlet;

public class Test2Servlet implements HttpServlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.writeBody("Test2Servlet");
        response.flush();
    }
}
