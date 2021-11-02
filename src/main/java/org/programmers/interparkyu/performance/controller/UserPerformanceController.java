package org.programmers.interparkyu.performance.controller;

import static org.programmers.interparkyu.performance.controller.UserPerformanceController.performanceRequestBaseUri;

import java.text.MessageFormat;
import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.common.dto.ApiResponse;
import org.programmers.interparkyu.performance.dto.response.BriefPerformanceResponse;
import org.programmers.interparkyu.performance.dto.response.DetailPerformanceResponse;
import org.programmers.interparkyu.performance.dto.response.PerformanceSummaryResponse;
import org.programmers.interparkyu.performance.dto.response.RoundDateResponse;
import org.programmers.interparkyu.performance.service.RoundService;
import org.programmers.interparkyu.performance.service.UserPerformanceService;
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
    public ApiResponse<List<BriefPerformanceResponse>> allPerformanceList() {
        // TODO 2021.10.29 TI-86 : 현재는 개발 편의성을 위해 로컬 테스트용 base url을 전달한다. 이후 실제 Url을 전달할 수 있도록 수정해야 한다.
        String requestBaseUrl = "localhost:8080";
        return ApiResponse.ok(
            performanceRequestBaseUri,
            userPerformanceService.getAllOnStagePerformanceList(
                requestBaseUrl + performanceRequestBaseUri)
        );
    }

    @GetMapping("/{performanceId}")
    public ApiResponse<DetailPerformanceResponse> performanceDetail(
        final @PathVariable Long performanceId) {
        PerformanceSummaryResponse summary =
            userPerformanceService.getPerformanceSummary(performanceId);
        List<RoundDateResponse> rounds = roundService.getAll(performanceId);
        return ApiResponse.ok(
            MessageFormat.format("{0}/{1}", performanceRequestBaseUri, performanceId),
            DetailPerformanceResponse.from(summary, rounds)
        );
    }
}
