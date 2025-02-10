package com.piopoi.was_sync_nonblocking;

import com.piopoi.was_sync_nonblocking.core.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WasSyncNonblockingMain {
    private static final Logger logger = LoggerFactory.getLogger(WasSyncNonblockingMain.class);

    public static void main(String[] args) {
        logger.info("Starting Server...");
        HttpServer server = new HttpServer();
        server.start();
    }
}
