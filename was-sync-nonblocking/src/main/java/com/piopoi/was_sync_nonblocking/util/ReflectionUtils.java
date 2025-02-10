package com.piopoi.was_sync_nonblocking.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;

public class ReflectionUtils {
    private static final String ROOT_PACKAGE = "com.piopoi.was_sync_nonblocking";

    /**
     * 지정된 인터페이스의 모든 구현체 인스턴스를 반환합니다.
     *
     * @param interfaceType 검색할 인터페이스 클래스
     * @return 지정된 인터페이스의 모든 구현체의 리스트
     */
    public static <T> List<T> getImplementations(Class<T> interfaceType) {
        Reflections reflections = new Reflections(ROOT_PACKAGE);
        Set<Class<? extends T>> implementations = reflections.getSubTypesOf(interfaceType);
        return implementations.stream()
                .map(ReflectionUtils::createInstance)
                .collect(Collectors.toList());
    }


    /**
     * 주어진 클래스 이름과 타입의 인스턴스를 생성합니다.
     *
     * @param className 인스턴스를 생성할 클래스 이름
     * @param type      인스턴스의 타입
     * @return 생성된 인스턴스
     */
    public static <T> T createInstance(String className, Class<T> type) {
        try {
            Class<?> clazz = Class.forName(className);
            if (!type.isAssignableFrom(clazz)) {
                throw new RuntimeException("Class is not assignable from: " + className);
            }
            return type.cast(createInstance(clazz));
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance for: " + className, e);
        }
    }

    /**
     * 주어진 클래스의 인스턴스를 생성합니다.
     *
     * @param clazz 객체를 생성할 클래스
     * @param <T>   생성된 객체의 타입
     * @return 생성된 인스턴스
     */
    public static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance for: " + clazz.getName(), e);
        }
    }
}
