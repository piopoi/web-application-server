package com.piopoi.was_sync_blocking.security;

import com.piopoi.was_sync_blocking.core.HttpRequest;
import com.piopoi.was_sync_blocking.exception.BadRequestException;
import com.piopoi.was_sync_blocking.util.ReflectionUtils;
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
