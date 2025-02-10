package com.piopoi.was_async_nonblocking.security;

import com.piopoi.was_async_nonblocking.core.HttpRequest;
import com.piopoi.was_async_nonblocking.exception.BadRequestException;

public interface SecurityRule {
    void isViolated(HttpRequest request) throws BadRequestException;
}
