package com.piopoi.was_sync_blocking.security;

import com.piopoi.was_sync_blocking.core.HttpRequest;
import com.piopoi.was_sync_blocking.exception.BadRequestException;
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
        extensions.stream()
                .filter(extension -> path.endsWith("." + extension))
                .findAny()
                .orElseThrow(() -> new BadRequestException("Forbidden file extension detected: path=" + path));
    }
}
