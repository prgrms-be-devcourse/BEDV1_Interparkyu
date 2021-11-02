package org.programmers.interparkyu.performance.dto.response;

import lombok.Builder;
import org.programmers.interparkyu.performance.domain.Performance;
import org.programmers.interparkyu.performance.domain.PerformanceCategory;

public record PerformanceSummaryResponse(

    String title,

    PerformanceCategory category,

    Integer runtime,

    String hallName

) {

    @Builder
    public PerformanceSummaryResponse {}

    public static PerformanceSummaryResponse from(Performance performance) {
        return PerformanceSummaryResponse.builder()
            .title(performance.getTitle())
            .category(performance.getCategory())
            .runtime(performance.getRuntime())
            .hallName(performance.getHall().getName())
            .build();
    }

}
