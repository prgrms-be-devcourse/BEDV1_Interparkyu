package org.programmers.interparkyu.performance.controller;

import java.util.List;
import org.programmers.interparkyu.ApiResponse;
import org.programmers.interparkyu.performance.UserPerformanceService;
import org.programmers.interparkyu.performance.dto.BriefPerformanceInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
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
    
    @GetMapping("/v1/performances")
    public ApiResponse<List<BriefPerformanceInfo>> allPerformanceList() {
        List<BriefPerformanceInfo> allPerformance = service.getAllPerformance();
        return ApiResponse.ok("/v1/performances", allPerformance);
    }
}
