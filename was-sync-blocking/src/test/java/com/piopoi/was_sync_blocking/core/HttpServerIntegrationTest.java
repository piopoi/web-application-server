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
    private static TestHttpClient httpClient;

    @BeforeAll
    public static void setUp() throws Exception {
        ServerConfig serverConfig = ServerConfig.load();
        port = serverConfig.getPort();

        // 클라이언트와 서버의 스레드를 분리하기 위해 다른 스레드에서 HttpServer 실행.
        executorService = Executors.newSingleThreadExecutor();
        httpServer = new HttpServer();
        executorService.submit(() -> httpServer.start());
        Thread.sleep(1000); //서버가 완전히 시작될 대까지 잠시 대기

        httpClient = new TestHttpClient(port, HOST);
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
        TestHttpRequest request = buildRequest(PATH);

        // when
        TestHttpResponse response = httpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.getCode());
        assertThat(response.getBody().contains(BAD_REQUEST.getDefaultResponseBody())).isTrue();
    }

    @DisplayName("HTTP_ROOT 디렉터리의 상위 디렉터리에 접근할 수 없다.")
    @Test
    public void invalidDirectory() throws IOException {
        // given
        TestHttpRequest request = buildRequest("/../../index.html");

        // when
        TestHttpResponse response = httpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.getCode());
        assertThat(response.getBody().contains(BAD_REQUEST.getDefaultResponseBody())).isTrue();
    }

    @DisplayName("설정된 확장자가 아닌 파일은 요청할 수 없다.")
    @Test
    void invalidExtension() throws IOException {
        // given
        TestHttpRequest request = buildRequest("/index.exe");

        // when
        TestHttpResponse response = httpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.getCode());
        assertThat(response.getBody().contains(BAD_REQUEST.getDefaultResponseBody())).isTrue();
    }

    @DisplayName("설정에 매핑된 서블릿을 호출할 수 있다.")
    @Test
    void callServlet() throws IOException {
        // given
        TestHttpRequest request1 = buildRequest("/Test1");
        TestHttpRequest request2 = buildRequest("/Test2");

        // when
        TestHttpResponse response1 = httpClient.sendRequest(request1);
        TestHttpResponse response2 = httpClient.sendRequest(request2);

        // then
        assertThat(response1.getStatusCode()).isEqualTo(OK.getCode());
        assertThat(response1.getBody()).contains("Test1Servlet");
        assertThat(response2.getStatusCode()).isEqualTo(OK.getCode());
        assertThat(response2.getBody()).contains("Test2Servlet");
    }

    @DisplayName("설정에 매핑된 서블릿을 호출할 수 있다.")
    @Test
    void callServlet_invalid() throws IOException {
        // given
        TestHttpRequest request = buildRequest("/Invalid");

        // when
        TestHttpResponse response = httpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.getCode());
    }

    private TestHttpRequest buildRequest(String path) {
        return TestHttpRequest.builder()
                .method(METHOD.name())
                .path(path)
                .httpVersion(HTTP_VERSION)
                .build();
    }
}
