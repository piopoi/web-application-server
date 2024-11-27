package com.piopoi.was_sync_blocking.util;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils {
    public static final String ROOT_DIRECTORY = "static";

    public static String readResource(String filePath) throws IOException {
        ClassLoader classLoader = ResourceUtils.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(getFilePath(filePath))) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + filePath);
            }
            return new String(inputStream.readAllBytes());
        }
    }

    public static boolean isResourceExists(String filePath) {
        ClassLoader classLoader = ResourceUtils.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(getFilePath(filePath))) {
            return inputStream != null;
        } catch (IOException e) {
            return false;
        }
    }

    private static String getFilePath(String filePath) {
        return ROOT_DIRECTORY + filePath;
    }
}
