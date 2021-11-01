package org.programmers.interparkyu.performance.dto;

import lombok.Builder;
import org.programmers.interparkyu.performance.Performance;
import org.programmers.interparkyu.performance.PerformanceCategory;

public record PerformanceSummary(
    String title,
    PerformanceCategory category,
    Integer runtime,
    String hallName
) {

    @Builder
    public PerformanceSummary { }

    public static PerformanceSummary from(Performance performance) {
        return PerformanceSummary.builder()
            .title(performance.getTitle())
            .category(performance.getCategory())
            .runtime(performance.getRuntime())
            .hallName(performance.getHall().getName())
            .build();
    }
}
