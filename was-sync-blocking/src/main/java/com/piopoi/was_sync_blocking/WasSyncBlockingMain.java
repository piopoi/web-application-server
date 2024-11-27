package com.piopoi.was_sync_blocking;

import com.piopoi.was_sync_blocking.core.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WasSyncBlockingMain {
    private static final Logger logger = LoggerFactory.getLogger(WasSyncBlockingMain.class);

    public static void main(String[] args) {
        logger.info("Starting Server...");
        HttpServer server = new HttpServer();
        server.start();
    }
}
