package com.piopoi.was_sync_blocking.config;

import com.piopoi.was_sync_blocking.exception.InvalidConfigurationException;
import java.io.IOException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class ServerConfig {
    private static final String SERVER_CONFIG_FILENAME = "server.yaml";
    private static final int MIN_PORT = 1;
    private static final int MAX_PORT = 65535;
    private static final int MIN_THREAD_POOL_SIZE = 1;

    private int port;
    private int threadPoolSize;

    private ServerConfig() {
    }

    private static final class ConfigHolder {
        private static final ServerConfig INSTANCE;

        static {
            try {
                ServerConfig serverConfig = ConfigLoader.loadServerConfig(SERVER_CONFIG_FILENAME);
                serverConfig.validate();
                INSTANCE = serverConfig;
            } catch (IOException e) {
                throw new ExceptionInInitializerError("Failed to load config: " + e.getMessage());
            }
        }
    }

    public static ServerConfig getInstance() {
        return ConfigHolder.INSTANCE;
    }

    private void validate() {
        try {
            validatePort();
            validateThreadPoolSize();
        } catch (InvalidConfigurationException e) {
            log.error(e.getMessage());
        }
    }

    private void validatePort() throws InvalidConfigurationException {
        if (port < MIN_PORT || port > MAX_PORT) {
            throw new InvalidConfigurationException("Port value " + port + " is out of range. Valid range is " + MIN_PORT + " to " + MAX_PORT + ".");
        }
    }

    private void validateThreadPoolSize() throws InvalidConfigurationException {
        if (threadPoolSize < MIN_THREAD_POOL_SIZE) {
            throw new InvalidConfigurationException(
                    "Thread pool size " + threadPoolSize + " is too small. Minimum accepted size is " + MIN_THREAD_POOL_SIZE + ".");
        }
    }
}
