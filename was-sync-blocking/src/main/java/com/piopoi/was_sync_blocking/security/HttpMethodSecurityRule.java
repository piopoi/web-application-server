package com.piopoi.was_sync_blocking.security;

import static com.piopoi.was_sync_blocking.core.HttpMethod.*;

import com.piopoi.was_sync_blocking.core.HttpMethod;
import com.piopoi.was_sync_blocking.core.HttpRequest;
import com.piopoi.was_sync_blocking.exception.BadRequestException;
import java.util.ArrayList;
import java.util.List;

/**
 * 보안 규칙: 설정된 HTTP Method에 대한 요청만 허용한다.
 */
public class HttpMethodSecurityRule implements SecurityRule {
    private final List<HttpMethod> httpMethods = new ArrayList<>();

    public HttpMethodSecurityRule() {
        httpMethods.add(GET);
    }

    @Override
    public void isViolated(HttpRequest request) throws BadRequestException {
        HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());
        if (!httpMethods.contains(requestMethod)) {
            throw new BadRequestException("HTTP method is not allowed: requestMethod=" + requestMethod.name());
        }
    }
}
