package com.piopoi.was_sync_blocking.security;


import com.piopoi.was_sync_blocking.core.HttpRequest;
import com.piopoi.was_sync_blocking.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;

/**
 * 보안 규칙: HTTP_ROOT 디렉터리의 상위 디렉터리에 접근은 거부한다.
 */
@Slf4j
public class DirectorySecurityRule implements SecurityRule {
    @Override
    public void isViolated(HttpRequest request) throws BadRequestException {
        String path = request.getPath();
        if (path.contains("/../")) {
            String errorMessage = "Forbidden directory access detected: path=" + path;
            log.error(errorMessage);
            throw new BadRequestException(errorMessage);
        }
    }
}
