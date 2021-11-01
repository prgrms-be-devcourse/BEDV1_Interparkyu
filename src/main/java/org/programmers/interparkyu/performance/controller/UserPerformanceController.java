package org.programmers.interparkyu.performance.controller;

import static org.programmers.interparkyu.performance.controller.UserPerformanceController.performanceRequestBaseUri;

import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.ApiResponse;
import org.programmers.interparkyu.performance.dto.BriefPerformanceInfo;
import org.programmers.interparkyu.performance.dto.RoundDateResponse;
import org.programmers.interparkyu.performance.service.UserPerformanceService;
import org.programmers.interparkyu.performance.service.RoundService;
import org.programmers.interparkyu.performance.dto.DetailPerformanceInfo;
import org.programmers.interparkyu.performance.dto.PerformanceSummary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(performanceRequestBaseUri)
public class UserPerformanceController {

    public static final String performanceRequestBaseUri = "/v1/performances";

    private final UserPerformanceService userPerformanceService;

    private final RoundService roundService;
    
    @GetMapping
    public ApiResponse<List<BriefPerformanceInfo>> allPerformanceList() {
        // TODO 2021.10.29 TI-86 : 현재는 개발 편의성을 위해 로컬 테스트용 base url을 전달한다. 이후 실제 Url을 전달할 수 있도록 수정해야 한다.
        String requestBaseUrl = "localhost:8080";
        return ApiResponse.ok(performanceRequestBaseUri, userPerformanceService.getAllOnStagePerformanceList(requestBaseUrl + performanceRequestBaseUri));
    }

    @GetMapping("/{performanceId}")
    public ApiResponse<DetailPerformanceInfo> performanceDetail(@PathVariable Long performanceId) {
        PerformanceSummary summary = userPerformanceService.getPerformanceById(performanceId);
        List<RoundDateResponse> rounds = roundService.getAllRoundByPerformanceId(performanceId);
        return ApiResponse.ok(String.format("%s/%s", performanceRequestBaseUri, performanceId), DetailPerformanceInfo.from(summary, rounds));
    }
}
