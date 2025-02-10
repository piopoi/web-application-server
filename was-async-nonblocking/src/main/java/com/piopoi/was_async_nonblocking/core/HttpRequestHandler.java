package com.piopoi.was_async_nonblocking.core;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRequestHandler implements Runnable {

    private final Socket socket;

    public HttpRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (socket;
             InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream(), UTF_8);
             BufferedReader reader = new BufferedReader(inputStreamReader);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), false, UTF_8)) {

            HttpRequest request = new HttpRequest(reader);
            HttpResponse response = new HttpResponse(writer);

            DispatcherServlet dispatcherServlet = new DispatcherServlet();
            dispatcherServlet.dispatch(request, response);
        } catch (IOException e) {
            log.error("Error handling request", e);
        }
    }
}
