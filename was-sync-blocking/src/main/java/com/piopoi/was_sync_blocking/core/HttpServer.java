package com.piopoi.was_sync_blocking.core;

import com.piopoi.was_sync_blocking.config.ServerConfig;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpServer {
    private final ServerConfig serverConfig;
    private ExecutorService executorService;
    private ServerSocket serverSocket;
    private volatile boolean running = false;

    public HttpServer() {
        this.serverConfig = ServerConfig.load();
    }

    public void start() {
        int port = serverConfig.getPort();
        int connectionPoolSize = serverConfig.getConnectionPoolSize();
        executorService = Executors.newFixedThreadPool(connectionPoolSize);

        try {
            serverSocket = new ServerSocket(port);
            log.info("Server started on port {}", port);

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new HttpRequestHandler(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            stop();
        }
    }

    public void stop() {
        running = false;
        executorService.shutdown();
        closeServerSocket();
    }

    private void closeServerSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            log.error("Error closing server socket", e);
        }
    }
}
