package org.programmers.interparkyu;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonData {
    private final String message;

    private final String requestUri;

    // TODO: 2021.10.27 김휘년 TI-67 : timezone = "Asia/Seoul" 걸어주나 안걸어주나 반환값이 같다. 왜 그런지 찾아봐야 한다.
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    private final Integer internalHttpStatusCode;

    @Builder
    public CommonData(String message, String requestUri, HttpStatus statusCode) {
        this.message = message;
        this.requestUri = requestUri;
        this.internalHttpStatusCode = statusCode.value();
    }
}
