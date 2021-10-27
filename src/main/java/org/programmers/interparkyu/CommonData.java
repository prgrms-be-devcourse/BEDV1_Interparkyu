package org.programmers.interparkyu;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonData {
    private final String message;

    private final String requestUri;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "Asia/Seoul")
    private final LocalDateTime timestamp = LocalDateTime.now();

    private final Integer internalHttpStatusCode;

    @Builder
    public CommonData(String message, String requestUri, Integer statusCode) {
        this.message = message;
        this.requestUri = requestUri;
        this.internalHttpStatusCode = statusCode;
    }
}
