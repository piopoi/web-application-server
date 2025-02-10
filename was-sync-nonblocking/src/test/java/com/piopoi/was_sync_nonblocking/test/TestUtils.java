package com.piopoi.was_sync_nonblocking.test;

import com.piopoi.was_sync_nonblocking.config.ServerConfig;
import java.lang.reflect.Field;

public class TestUtils {

    public static <T> T getInaccessibleFieldValue(String fieldName, Class<T> fieldType) throws NoSuchFieldException, IllegalAccessException {
        Field minPortField = ServerConfig.class.getDeclaredField(fieldName);
        minPortField.setAccessible(true);
        return fieldType.cast(minPortField.get(null));
    }
}
