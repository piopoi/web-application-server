package com.piopoi.was_sync_blocking.core;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestHttpResponse {
    private String responseLine;
    private final Map<String, String> headers = new HashMap<>();
    private String body;

    public int getStatusCode() {
        return Integer.parseInt(responseLine.split(" ")[1]);
    }
}
