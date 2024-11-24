package com.piopoi.was_sync_blocking.core;

import static com.piopoi.was_sync_blocking.core.HttpServerConstants.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TestHttpRequest {
    private int port;
    private String host;
    private String method;
    private String path;
    private String httpVersion;

    public String getRequestMessage() {
        return method + " " + path + " " + httpVersion + CRLF +
                "Host: " + host + CRLF +
                CRLF;
    }
}
