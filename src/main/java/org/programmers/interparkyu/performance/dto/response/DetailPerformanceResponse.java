package org.programmers.interparkyu.performance.dto.response;


import java.util.List;
import lombok.Builder;
import org.programmers.interparkyu.performance.domain.PerformanceCategory;

public record DetailPerformanceResponse(

    String title,

    PerformanceCategory category,

    Integer runtime,

    String hallName,

    List<RoundDateResponse> roundDate

) {

    @Builder
    public DetailPerformanceResponse {}

    public static DetailPerformanceResponse from(PerformanceSummaryResponse summary,
        List<RoundDateResponse> date) {
        return DetailPerformanceResponse.builder()
            .title(summary.title())
            .category(summary.category())
            .runtime(summary.runtime())
            .hallName(summary.hallName())
            .roundDate(date)
            .build();
    }

}
