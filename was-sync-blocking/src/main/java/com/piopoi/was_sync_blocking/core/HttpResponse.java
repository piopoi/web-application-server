package com.piopoi.was_sync_blocking.core;

import static com.piopoi.was_sync_blocking.core.HttpServerConstants.*;
import static com.piopoi.was_sync_blocking.core.HttpStatus.*;
import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HttpResponse {
    private final PrintWriter writer;
    private HttpStatus httpStatus;
    private String contentType;
    private StringBuilder body;

    public HttpResponse(PrintWriter writer) {
        this.writer = writer;
        this.httpStatus = OK;
        this.contentType = CONTENT_TYPE_TEXT_HTML;
        this.body = new StringBuilder();
    }

    public void writeBody(String body) {
        this.body.append(body);
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
    }

    private int getContentLength() {
        return writer.toString().getBytes(UTF_8).length;
    }

    private String getDateHeader() {
        return ZonedDateTime.now(TimeZone.getTimeZone("GMT").toZoneId())
                .format(DateTimeFormatter.RFC_1123_DATE_TIME.withLocale(Locale.US));
    }
}
