package com.piopoi.was_sync_nonblocking.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestHttpClient {
    private final int port;
    private final String host;

    public TestHttpClient(int port, String host) {
        this.port = port;
        this.host = host;
    }

    public TestHttpResponse sendRequest(TestHttpRequest request) throws IOException {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.print(request.getRequestMessage());
            out.flush();

            TestHttpResponse response = new TestHttpResponse();

            // 상태 라인 파싱
            response.setResponseLine(in.readLine());

            // 헤더 파싱
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                String[] parts = line.split(": ", 2);
                response.getHeaders().put(parts[0], parts[1]);
            }

            // 본문 파싱
            StringBuilder body = new StringBuilder();
            while ((line = in.readLine()) != null) {
                body.append(line).append("\n");
            }
            response.setBody(body.toString());

            return response;
        }
    }
}
