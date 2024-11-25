package com.piopoi.was_sync_blocking.core;

import static com.piopoi.was_sync_blocking.core.HttpServerConstants.*;
import static com.piopoi.was_sync_blocking.core.HttpStatus.*;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import lombok.Setter;

@Setter
public class HttpResponse {
    private final PrintWriter writer;
    private HttpStatus httpStatus = OK;
    private String contentType = CONTENT_TYPE_TEXT_HTML;
    private final Map<String, String> headers = new HashMap<>();
    private final StringBuilder body = new StringBuilder();

    public HttpResponse(PrintWriter writer) {
        this.writer = writer;
    }

    public void writeHeader(String key, String value) {
        headers.put(key, value);
    }

    public void writeBody(String body) {
        this.body.append(body);
    }

    public void clearHeadersAndBody() {
        headers.clear();
        body.setLength(0);
    }

    public void flush() {
        writeHeader();
        writer.println();
        writer.println(body);
        writer.flush();
    }

    private void writeHeader() {
        writer.println(HTTP_VERSION_1_1 + " " + httpStatus.getCode() + " " + httpStatus.getDescription());
        writer.println("Date: " + getDateHeader());
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Length: " + getContentLength());
        headers.forEach((key, value) -> writer.println(key + ": " + value));
    }

    private int getContentLength() {
        return writer.toString().getBytes(UTF_8).length;
    }

    private String getDateHeader() {
        return ZonedDateTime.now(TimeZone.getTimeZone("GMT").toZoneId())
                .format(DateTimeFormatter.RFC_1123_DATE_TIME.withLocale(Locale.US));
    }
}
