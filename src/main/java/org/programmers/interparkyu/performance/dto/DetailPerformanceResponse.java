package org.programmers.interparkyu.performance.dto;


import java.util.List;
import lombok.Builder;
import org.programmers.interparkyu.performance.PerformanceCategory;

public record DetailPerformanceResponse(
    String title,
    PerformanceCategory category,
    Integer runtime,
    List<RoundDateResponse> roundDate
) {

    @Builder
    public DetailPerformanceResponse { }

    public static DetailPerformanceResponse from(PerformanceSummary summary, List<RoundDateResponse> date) {
        return DetailPerformanceResponse.builder()
            .title(summary.title())
            .category(summary.category())
            .runtime(summary.runtime())
            .roundDate(date)
            .build();
    }
}
