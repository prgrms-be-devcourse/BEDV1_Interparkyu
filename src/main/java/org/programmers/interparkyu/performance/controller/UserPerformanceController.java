package org.programmers.interparkyu.performance.controller;

import java.util.List;
import org.programmers.interparkyu.ApiResponse;
import org.programmers.interparkyu.performance.dto.BriefPerformanceInfo;
import org.programmers.interparkyu.performance.service.UserPerformanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserPerformanceController {

    private final UserPerformanceService service;

    public UserPerformanceController(UserPerformanceService service) {
        this.service = service;
    }
    
    @GetMapping("/v1/performances")
    public ApiResponse<List<BriefPerformanceInfo>> allPerformanceList() {
        return ApiResponse.ok("/v1/performances", service.getAllOnStagePerformanceList());
    }
}
