package com.piopoi.was_sync_blocking.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;

public class ReflectionUtils {
    private static final String ROOT_PACKAGE = "com.piopoi.was_sync_blocking";

    /**
     * 지정된 인터페이스의 모든 구현체 인스턴스를 반환합니다.
     *
     * @param interfaceClass 검색할 인터페이스 클래스
     * @return 지정된 인터페이스의 모든 구현체의 리스트
     */
    public static <T> List<T> getImplementations(Class<T> interfaceClass) {
        Reflections reflections = new Reflections(ROOT_PACKAGE);
        Set<Class<? extends T>> implementations = reflections.getSubTypesOf(interfaceClass);

        return implementations.stream()
                .map(clazz -> {
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to create instance for: " + clazz.getName(), e);
                    }
                })
                .collect(Collectors.toList());
    }
}
