package com.piopoi.was_sync_blocking.core;

import static com.piopoi.was_sync_blocking.core.HttpMethod.*;
import static com.piopoi.was_sync_blocking.core.HttpServerConstants.*;
import static com.piopoi.was_sync_blocking.core.HttpStatus.*;
import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.*;

import com.piopoi.was_sync_blocking.config.ServerConfig;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpServerIntegrationTest {
    private static final String HOST = "localhost";
    private final HttpMethod METHOD = GET;
    private final String PATH = "/";
    private final String HTTP_VERSION = HTTP_VERSION_1_1;

    private static ExecutorService executorService;
    private static int port;
    private static HttpServer httpServer;
    private static TestHttpClient testHttpClient;

    @BeforeAll
    public static void setUp() throws Exception {
        ServerConfig serverConfig = ServerConfig.load();
        port = serverConfig.getPort();

        // 클라이언트와 서버의 스레드를 분리하기 위해 다른 스레드에서 HttpServer 실행.
        executorService = Executors.newSingleThreadExecutor();
        httpServer = new HttpServer();
        executorService.submit(() -> httpServer.start());
        Thread.sleep(1000); //서버가 완전히 시작될 대까지 잠시 대기

        testHttpClient = new TestHttpClient(port, HOST);
    }

    @AfterAll
    public static void shutDown() throws Exception {
        httpServer.stop();
        executorService.shutdown();
        if (!executorService.awaitTermination(3000, SECONDS)) {
            System.err.println("서버가 정상적으로 종료되지 않았습니다.");
            Thread.sleep(1000); // 포트가 해제될 때까지 잠시 대기
        }
    }

    @DisplayName("허용되지 않은 HttpMethod 로 요청할 수 없다.")
    @Test
    public void invalidMethod() throws IOException {
        // given
        TestHttpRequest request = buildRequest(POST, PATH, HTTP_VERSION);

        // when
        TestHttpResponse response = testHttpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.getCode());
        assertThat(response.getBody().contains(BAD_REQUEST.getDefaultResponseBody())).isTrue();
    }

    @DisplayName("HTTP_ROOT 디렉터리의 상위 디렉터리에 접근할 수 없다.")
    @Test
    public void invalidDirectory() throws IOException {
        // given
        TestHttpRequest request = buildRequest(GET, "/../../index.html", HTTP_VERSION);

        // when
        TestHttpResponse response = testHttpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.getCode());
        assertThat(response.getBody().contains(BAD_REQUEST.getDefaultResponseBody())).isTrue();
    }

    @DisplayName("설정된 확장자가 아닌 파일은 요청할 수 없다.")
    @Test
    public void invalidExtension() throws IOException {
        // given
        TestHttpRequest request = buildRequest(GET, "/index.exe", HTTP_VERSION);

        // when
        TestHttpResponse response = testHttpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.getCode());
        assertThat(response.getBody().contains(BAD_REQUEST.getDefaultResponseBody())).isTrue();
    }

    private TestHttpRequest buildRequest(HttpMethod httpMethod, String path, String httpVersion) {
        return TestHttpRequest.builder()
                .method(httpMethod.name())
                .path(path)
                .httpVersion(httpVersion)
                .build();
    }
}
