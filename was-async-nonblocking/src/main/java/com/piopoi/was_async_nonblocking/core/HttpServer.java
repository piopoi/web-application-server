package com.piopoi.was_async_nonblocking.core;

import com.piopoi.was_async_nonblocking.config.ServerConfig;
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
    private volatile boolean running = true;

    public HttpServer() {
        this.serverConfig = ServerConfig.load();
    }

    public void start() {
        try {
            int port = serverConfig.getPort();
            log.info("Server port: {}", port);

            int connectionPoolSize = serverConfig.getConnectionPoolSize();
            executorService = Executors.newFixedThreadPool(connectionPoolSize);
            log.info("Connection pool size: {}", connectionPoolSize);

            serverSocket = new ServerSocket(port);
            log.info("Starting server on port {}", port);

            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    log.info("Accepted connection from {}", socket.getRemoteSocketAddress());

                    executorService.submit(new HttpRequestHandler(socket));
                } catch (IOException e) {
                    if (!running) {
                        log.info("Server socket closed.");
                    } else {
                        log.error("Error accepting connection.", e);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error starting server socket", e);
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
