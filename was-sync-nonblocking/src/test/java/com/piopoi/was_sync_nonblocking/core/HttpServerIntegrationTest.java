package com.piopoi.was_sync_nonblocking.core;

import static com.piopoi.was_sync_nonblocking.core.HttpMethod.*;
import static com.piopoi.was_sync_nonblocking.core.HttpServerConstants.*;
import static com.piopoi.was_sync_nonblocking.core.HttpStatus.*;
import static com.piopoi.was_sync_nonblocking.test.TestConstants.*;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

import com.piopoi.was_sync_nonblocking.config.ServerConfig;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HttpServerIntegrationTest {
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

        httpClient = new TestHttpClient(port, "localhost");
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
    @DisplayName("매핑된 서블릿을 호출할 수 있다.")
    void requestServlet() throws IOException {
        // given
        TestHttpRequest request1 = buildRequest(GET, "/Test1");
        TestHttpRequest request2 = buildRequest(GET, "/Test2");

        // when
        TestHttpResponse response1 = httpClient.sendRequest(request1);
        TestHttpResponse response2 = httpClient.sendRequest(request2);

        // then
        assertThat(response1.getStatusCode()).isEqualTo(OK.getCode());
        assertThat(response1.getBody()).contains("Test1Servlet");
        assertThat(response2.getStatusCode()).isEqualTo(OK.getCode());
        assertThat(response2.getBody()).contains("Test2Servlet");
    }

    @Test
    @DisplayName("매핑되지 않은 서블릿을 요청하면 400을 반환한다.")
    void requestServlet_invalidPath() throws IOException {
        // given
        TestHttpRequest request = buildRequest(GET, "/InvalidServlet");

        // when
        TestHttpResponse response = httpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.getCode());
    }

    @Test
    @DisplayName("정적 컨텐츠 요청 시 해당 파일을 반환한다.")
    void requestStatic() throws IOException {
        // given
        TestHttpRequest request = buildRequest(GET, TEST_FILEPATH);

        // when
        TestHttpResponse response = httpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK.getCode());
        assertThat(response.getBody()).contains(TEST_CONTENT);
    }

    @Test
    @DisplayName("잘못된 정적 컨텐츠 요청 시 400을 반환한다.")
    void requestStatic_invalidPath() throws IOException {
        // given
        TestHttpRequest request = buildRequest(GET, TEST_FILEPATH);

        // when
        TestHttpResponse response = httpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK.getCode());
        assertThat(response.getBody()).contains(TEST_CONTENT);
    }

    @Test
    @DisplayName("허용되지 않은 HttpMethod로 요청 시 400을 반환한다.")
    public void securityFilter_invalidMethod() throws IOException {
        // given
        TestHttpRequest request = buildRequest(POST, TEST_FILEPATH);

        // when
        TestHttpResponse response = httpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.getCode());
        assertThat(response.getBody().contains(BAD_REQUEST.getDefaultResponseBody())).isTrue();
    }

    @Test
    @DisplayName("상위 디렉터리에 접근 요청 시 400을 반환한다.")
    public void securityFilter_invalidDirectory() throws IOException {
        // given
        TestHttpRequest request = buildRequest(GET, "/../../test.html");

        // when
        TestHttpResponse response = httpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.getCode());
        assertThat(response.getBody().contains(BAD_REQUEST.getDefaultResponseBody())).isTrue();
    }

    @Test
    @DisplayName("설정된 확장자가 아닌 파일 요청 시 400을 반환한다.")
    void securityFilter_invalidExtension() throws IOException {
        // given
        TestHttpRequest request = buildRequest(GET, "/index.exe");

        // when
        TestHttpResponse response = httpClient.sendRequest(request);

        // then
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.getCode());
        assertThat(response.getBody().contains(BAD_REQUEST.getDefaultResponseBody())).isTrue();
    }

    private TestHttpRequest buildRequest(HttpMethod method, String path) {
        return TestHttpRequest.builder()
                .method(method.name())
                .path(path)
                .httpVersion(HTTP_VERSION_1_1)
                .build();
    }
}
