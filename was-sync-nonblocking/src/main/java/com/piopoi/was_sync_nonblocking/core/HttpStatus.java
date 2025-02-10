package com.piopoi.was_sync_nonblocking.core;

import lombok.Getter;

@Getter
public enum HttpStatus {
    OK(200, "OK", ""),
    BAD_REQUEST(400, "Bad Request", "<h1>400 Bad Request</h1>"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "<h1>500 Internal Server Error</h1>");

    private final int code;
    private final String description;
    private final String defaultResponseBody;

    HttpStatus(int code, String description, String defaultResponseBody) {
        this.code = code;
        this.description = description;
        this.defaultResponseBody = defaultResponseBody;
    }
}
