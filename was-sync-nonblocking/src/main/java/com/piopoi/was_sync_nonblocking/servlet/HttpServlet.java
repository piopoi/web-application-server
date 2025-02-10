package com.piopoi.was_sync_nonblocking.servlet;

import com.piopoi.was_sync_nonblocking.core.HttpRequest;
import com.piopoi.was_sync_nonblocking.core.HttpResponse;

public interface HttpServlet {

    void service(HttpRequest request, HttpResponse response);
}
