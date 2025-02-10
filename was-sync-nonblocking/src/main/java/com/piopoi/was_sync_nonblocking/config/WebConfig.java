package com.piopoi.was_sync_nonblocking.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class WebConfig {
    private static final String WEB_CONFIG_FILENAME = "web.yaml";

    /**
     * key = URL Path
     * value = 패키지명을 포함한 Servlet 클래스명
     */
    private final List<Servlet> servlets = new ArrayList<>();

    private WebConfig() {
    }

    private static final class ConfigHolder {
        private static final WebConfig INSTANCE;

        static {
            try {
                INSTANCE = ConfigLoader.loadConfig(WebConfig.class, WEB_CONFIG_FILENAME);
            } catch (IOException e) {
                throw new ExceptionInInitializerError("Failed to load WebConfig: " + e.getMessage());
            }
        }
    }

    public static WebConfig load() {
        return ConfigHolder.INSTANCE;
    }

    public boolean isPathExist(String path) {
        return servlets.stream()
                .anyMatch(servlet -> servlet.getPath().equals(path));
    }

    public String getClassName(String path) {
        return servlets.stream()
                .filter(servlet -> path.equals(servlet.getPath()))
                .findFirst()
                .map(Servlet::getClassName)
                .orElse(null);
    }
}
