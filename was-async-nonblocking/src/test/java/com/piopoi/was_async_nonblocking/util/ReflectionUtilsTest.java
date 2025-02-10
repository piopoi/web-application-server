package com.piopoi.was_async_nonblocking.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReflectionUtilsTest {

    @Test
    @DisplayName("지정된 인터페이스의 모든 구현체 인스턴스를 반환한다")
    void getImplementations() {
        // given
        Class<ReflectionTestInterface> interfaceType = ReflectionTestInterface.class;

        // when
        List<ReflectionTestInterface> implementations = ReflectionUtils.getImplementations(interfaceType);

        // then
        assertThat(implementations).isNotNull().isNotEmpty();
        implementations.forEach(impl -> assertThat(impl).isInstanceOf(ReflectionTestInterface.class));
    }

    @Test
    @DisplayName("주어진 클래스 이름과 타입의 인스턴스를 생성한다")
    void createInstance_withClassName() {
        // given
        String className = "com.piopoi.was_async_nonblocking.util.ReflectionTestClass";
        Class<ReflectionTestClass> type = ReflectionTestClass.class;

        // when
        ReflectionTestClass instance = ReflectionUtils.createInstance(className, type);

        // then
        assertThat(instance).isNotNull();
        assertThat(instance).isInstanceOf(ReflectionTestClass.class);
    }

    @Test
    @DisplayName("주어진 클래스의 인스턴스를 생성한다")
    void createInstance_withClass() {
        // given
        Class<ReflectionTestClass> clazz = ReflectionTestClass.class;

        // when
        ReflectionTestClass instance = ReflectionUtils.createInstance(clazz);

        // then
        assertThat(instance).isNotNull();
        assertThat(instance).isInstanceOf(ReflectionTestClass.class);
    }
}
