package com.piopoi.was_sync_nonblocking.util;

import static com.piopoi.was_sync_nonblocking.test.TestConstants.TEST_CONTENT;
import static com.piopoi.was_sync_nonblocking.test.TestConstants.TEST_FILEPATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResourceUtilsTest {

    @Test
    @DisplayName("자원 읽기 테스트")
    void testReadResource() {
        try {
            // when
            String content = ResourceUtils.readResource(TEST_FILEPATH);

            // then
            assertThat(content).isNotEmpty();
            assertThat(content).contains(TEST_CONTENT);
        } catch (IOException e) {
            fail("IOException should not occur: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("자원 읽기 - 파일 없음 테스트")
    void testReadResource_FileNotFound() {
        // when then
        assertThatThrownBy(() -> ResourceUtils.readResource("/not_found.html"))
                .isInstanceOf(IOException.class);
    }

    @Test
    @DisplayName("자원 존재 여부 확인 - 존재하는 파일")
    void testIsResourceExists_ExistingFile() {
        // when
        boolean exists = ResourceUtils.isResourceExists(TEST_FILEPATH);

        //then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("자원 존재 여부 확인 - 존재하지 않는 파일")
    void testIsResourceExists_NonExistingFile() {
        // when
        boolean exists = ResourceUtils.isResourceExists("/not_found.html");

        // then
        assertThat(exists).isFalse();
    }
}
