package com.piopoi.was_sync_blocking.servlet;

import com.piopoi.was_sync_blocking.core.HttpRequest;
import com.piopoi.was_sync_blocking.core.HttpResponse;

public interface HttpServlet {

    void service(HttpRequest request, HttpResponse response);
}
