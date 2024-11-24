package com.piopoi.was_sync_blocking.core;

import static com.piopoi.was_sync_blocking.core.HttpServerConstants.*;
import static com.piopoi.was_sync_blocking.core.HttpStatus.OK;
import static java.util.concurrent.TimeUnit.*;
import static org.assertj.core.api.Assertions.*;

import com.piopoi.was_sync_blocking.config.ServerConfig;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HttpServerIntegrationTest {
    private static final String HOST = "localhost";
    private final String METHOD = "GET";
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

    @Test
    public void request() throws IOException {
        // given
        TestHttpRequest request = buildRequest(METHOD, PATH, HTTP_VERSION);

        // when
        TestHttpResponse response = testHttpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK.getCode());
        assertThat(response.getBody().contains("This is DispatcherServlet !!")).isTrue();
    }

    private TestHttpRequest buildRequest(String method, String path, String httpVersion) {
        return TestHttpRequest.builder()
                .method(method)
                .path(path)
                .httpVersion(httpVersion)
                .build();
    }
}
