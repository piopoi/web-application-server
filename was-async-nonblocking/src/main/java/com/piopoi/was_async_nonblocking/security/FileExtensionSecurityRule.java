package com.piopoi.was_async_nonblocking.security;

import com.piopoi.was_async_nonblocking.core.HttpRequest;
import com.piopoi.was_async_nonblocking.exception.BadRequestException;
import java.util.ArrayList;
import java.util.List;

/**
 * 보안 규칙: 설정된 확장자의 파일에 대한 요청만 허용한다.
 */
public class FileExtensionSecurityRule implements SecurityRule {
    private static final String FILE_EXTENSION_HTML = "html";

    private final List<String> extensions = new ArrayList<>();

    public FileExtensionSecurityRule() {
        extensions.add(FILE_EXTENSION_HTML);
    }

    @Override
    public void isViolated(HttpRequest request) throws BadRequestException {
        String path = request.getPath();
        if (!path.contains(".")) {
            return;
        }
        extensions.stream()
                .filter(extension -> path.endsWith("." + extension))
                .findAny()
                .orElseThrow(() -> new BadRequestException("Forbidden file extension detected: path=" + path));
    }
}
