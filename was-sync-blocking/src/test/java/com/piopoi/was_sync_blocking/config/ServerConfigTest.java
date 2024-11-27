package com.piopoi.was_sync_blocking.config;

import static com.piopoi.was_sync_blocking.test.TestUtils.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ServerConfigTest {

    @Test
    @DisplayName("서버 설정을 load 할 수 있다.")
    void load() throws NoSuchFieldException, IllegalAccessException {
        // when
        ServerConfig serverConfig = ServerConfig.load();

        // then
        int minPort = getInaccessibleFieldValue("MIN_PORT", Integer.class);
        int maxPort = getInaccessibleFieldValue("MAX_PORT", Integer.class);
        int minThreadPoolSize = getInaccessibleFieldValue("MIN_CONNECTION_POOL_SIZE", Integer.class);

        assertThat(serverConfig).isNotNull();
        assertThat(serverConfig.getPort()).isBetween(minPort, maxPort);
        assertThat(serverConfig.getConnectionPoolSize()).isGreaterThanOrEqualTo(minThreadPoolSize);
    }
}