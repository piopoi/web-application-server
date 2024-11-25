package com.piopoi.was_sync_blocking.security;

import com.piopoi.was_sync_blocking.core.HttpRequest;
import com.piopoi.was_sync_blocking.exception.BadRequestException;

public interface SecurityRule {
    void isViolated(HttpRequest request) throws BadRequestException;
}
