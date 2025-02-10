package com.piopoi.was_sync_nonblocking.security;

import com.piopoi.was_sync_nonblocking.core.HttpRequest;
import com.piopoi.was_sync_nonblocking.exception.BadRequestException;

public interface SecurityRule {
    void isViolated(HttpRequest request) throws BadRequestException;
}
