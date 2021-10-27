package org.programmers.interparkyu;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    private CommonData common;
    private List<T> data = new ArrayList<>();

    public ApiResponse(CommonData common, T data) {
        this.common = common;
        this.data.add(data);
    }

    public static <T> ApiResponse<T> ok(String requestUri, T data) {
        CommonData common = new CommonData("success", requestUri, 200);
        return new ApiResponse<>(common, data);
    }

    public static <T> ApiResponse<T> fail(String failMessage, String requestUri, T data, Integer statusCode) {
        CommonData common = new CommonData(failMessage, requestUri, statusCode);
        return new ApiResponse<>(common, data);
    }
}
