package com.piopoi.was_sync_nonblocking.test;

import com.piopoi.was_sync_nonblocking.core.HttpRequest;
import com.piopoi.was_sync_nonblocking.core.HttpResponse;
import com.piopoi.was_sync_nonblocking.servlet.HttpServlet;

public class Test1Servlet implements HttpServlet {
    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.writeBody("Test1Servlet");
        response.flush();
    }
}
