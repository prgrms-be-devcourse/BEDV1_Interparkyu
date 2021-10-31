package org.programmers.interparkyu;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.interparkyu.error.ErrorResponse;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    private CommonData common;
    private final List<T> data = new ArrayList<>();

    private ApiResponse(CommonData common) {
        this.common = common;
    }

    private ApiResponse(CommonData common, T data) {
        this.common = common;
        this.data.add(data);
    }

    public static <T> ApiResponse<T> ok(String requestUri) {
        CommonData common = new CommonData("success", requestUri, HttpStatus.OK);
        return new ApiResponse<>(common);
    }

    public static <T> ApiResponse<T> ok(String requestUri, T data) {
        CommonData common = new CommonData("success", requestUri, HttpStatus.OK);
        return new ApiResponse<>(common, data);
    }

    public static <T> ApiResponse<T> fail(ErrorResponse response) {
        CommonData common = new CommonData(response.errorMessage(), response.requestUri(), response.status());
        return new ApiResponse<>(common);
    }
}
