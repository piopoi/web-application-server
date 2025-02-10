package com.piopoi.was_async_nonblocking;

import com.piopoi.was_async_nonblocking.core.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WasAsyncNonblockingMain {
    private static final Logger logger = LoggerFactory.getLogger(WasAsyncNonblockingMain.class);

    public static void main(String[] args) {
        logger.info("Starting Server...");
        HttpServer server = new HttpServer();
        server.start();
    }
}
