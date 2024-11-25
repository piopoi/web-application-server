package com.piopoi.was_sync_blocking.core;

import com.piopoi.was_sync_blocking.exception.ExceptionHandler;
import com.piopoi.was_sync_blocking.security.SecurityFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DispatcherServlet {

    private final SecurityFilter securityFilter;

    public DispatcherServlet() {
        this.securityFilter = new SecurityFilter();
    }

    public void dispatch(HttpRequest request, HttpResponse response) {
        try {
            // 1. 보안 규칙 체크
            securityFilter.check(request);

            // 2. 정적 컨텐츠 제공

            // 3. 동적 컨텐츠 제공

        } catch (Exception e) {
            dispatchException(e, request, response);
        }
    }

    private void dispatchException(Exception e, HttpRequest request, HttpResponse response) {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        exceptionHandler.handleException(e, request, response);
    }
}
