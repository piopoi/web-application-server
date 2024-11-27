package com.piopoi.was_sync_blocking.core;

import com.piopoi.was_sync_blocking.config.WebConfig;
import com.piopoi.was_sync_blocking.exception.BadRequestException;
import com.piopoi.was_sync_blocking.exception.ExceptionHandler;
import com.piopoi.was_sync_blocking.security.SecurityFilter;
import com.piopoi.was_sync_blocking.servlet.HttpServlet;
import com.piopoi.was_sync_blocking.util.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DispatcherServlet {

    private final SecurityFilter securityFilter;
    private final WebConfig webConfig;

    public DispatcherServlet() {
        this.securityFilter = new SecurityFilter();
        this.webConfig = WebConfig.load();
    }

    /**
     * 동작 방식
     * 1. 보안 규칙에 따라 모든 요청을 검사하여 규칙을 위반한 요청은 400 Bad Request를 반환한다.
     * 2. 정상적인 요청인 경우, web.yaml Path와 매핑된 서블릿을 실행한다.
     * 3. web.yaml에 매핑된 Path가 아니면, Path와 일치하는 경로의 정적 파일을 반환한다.
     * 4. 1~3에 해당하지 않으면, 400 Bad Request를 반환한다.
     */
    public void dispatch(HttpRequest request, HttpResponse response) {
        try {
            // 1. 보안 규칙 체크
            securityFilter.check(request);

            // 2. 동적 컨텐츠 제공
            if (isDynamicRequest(request)) {
                dispatchDynamic(request, response);
                return;
            }

            // 3. 정적 컨텐츠 제공
            dispatchStatic(request, response);

            dispathNotFound(request);
        } catch (Exception e) {
            dispatchException(e, request, response);
        }
    }

    private boolean isDynamicRequest(HttpRequest request) {
        return webConfig.isPathExist(request.getPath());
    }

    private void dispatchDynamic(HttpRequest request, HttpResponse response) {
        String servletClassName = webConfig.getClassName(request.getPath());
        invokeService(request, response, servletClassName);
    }

    private void dispatchStatic(HttpRequest request, HttpResponse response) {

    }

    private static void dispathNotFound(HttpRequest request) {
        throw new BadRequestException("Invalid request path: path=" + request.getPath());
    }

    private void dispatchException(Exception e, HttpRequest request, HttpResponse response) {
        ExceptionHandler exceptionHandler = new ExceptionHandler();
        exceptionHandler.handleException(e, request, response);
    }

    private static void invokeService(HttpRequest request, HttpResponse response, String servletClassName) {
        HttpServlet servlet = ReflectionUtils.createInstance(servletClassName, HttpServlet.class);
        servlet.service(request, response);
    }
}
