package com.piopoi.was_sync_nonblocking.security;

import com.piopoi.was_sync_nonblocking.core.HttpRequest;
import com.piopoi.was_sync_nonblocking.exception.BadRequestException;
import com.piopoi.was_sync_nonblocking.util.ReflectionUtils;
import java.util.List;
import lombok.Getter;

@Getter
public class SecurityFilter {
    private final List<SecurityRule> securityRules;

    public SecurityFilter() {
        this.securityRules = ReflectionUtils.getImplementations(SecurityRule.class);
    }

    public void check(HttpRequest request) throws BadRequestException {
        securityRules.forEach(securityRule -> securityRule.isViolated(request));
    }
}
