package org.programmers.interparkyu.performance.controller;

import java.util.List;
import org.programmers.interparkyu.ApiResponse;
import org.programmers.interparkyu.performance.UserPerformanceService;
import org.programmers.interparkyu.performance.dto.BriefPerformanceInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPerformanceController {

    private final UserPerformanceService service;

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e) {
        return ApiResponse.fail("Internal Server Error", "", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public UserPerformanceController(
        UserPerformanceService service) {
        this.service = service;
    }

    /*
    TODO: 2021.10.27 김휘년 TI-24
        1. 전체 공연 목록 받아오는 중 발생할 수 있는 에러는 서버측 에러밖에 안떠오름
            -> 좀 더 적절한 예외를 만들 수 있을지 생각 해 보기
        2. 응답 필드를 어느정도 통일하고 필요한 필드 외에는 Null로 해서 보내는게 좋을지,
            아니면 지금처럼 꼭 필요한 필드만 보내주는게 좋을지 잘 모르겠다.
    */
    @GetMapping("/v1/performances")
    public ApiResponse<List<BriefPerformanceInfo>> allPerformanceList() {
        List<BriefPerformanceInfo> allPerformance = service.getAllPerformance();
        return ApiResponse.ok("/v1/performances", allPerformance);
    }
}
