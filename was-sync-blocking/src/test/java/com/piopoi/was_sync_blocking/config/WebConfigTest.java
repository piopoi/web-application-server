package com.piopoi.was_sync_blocking.config;

import static com.piopoi.was_sync_blocking.test.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WebConfigTest {
    private WebConfig webConfig;

    @BeforeEach
    void setUp() {
        webConfig = WebConfig.load();
    }

    @Test
    @DisplayName("서블릿 매핑 설정을 load 할 수 있다.")
    void load() {
        // then
        assertThat(webConfig).isNotNull();
        assertThat(webConfig.getServlets()).hasSize(2);
        assertThat(webConfig.getServlets().get(0).getPath()).isEqualTo(TEST1_PATH);
        assertThat(webConfig.getServlets().get(0).getClassName()).isEqualTo(TEST1_CLASSNAME);
    }

    @Test
    @DisplayName("path가 서블릿 매핑된 path면 true를, 아니면 false를 반환한다.")
    void isPathExist() {
        // then
        assertThat(webConfig.isPathExist(TEST1_PATH)).isTrue();
        assertThat(webConfig.isPathExist(TEST2_PATH)).isTrue();
        assertThat(webConfig.isPathExist(TEST1_PATH.toLowerCase())).isFalse();
        assertThat(webConfig.isPathExist("/Invalid")).isFalse();
    }

    @Test
    @DisplayName("path에 매핑된 서블릿 클래스명을 반환한다.")
    void getClassName() {
        // then
        assertThat(webConfig.getClassName(TEST1_PATH)).isEqualTo(TEST1_CLASSNAME);
        assertThat(webConfig.getClassName(TEST2_PATH)).isEqualTo(TEST2_CLASSNAME);
        assertThat(webConfig.getClassName(TEST1_PATH.toLowerCase())).isNull();
    }
}
