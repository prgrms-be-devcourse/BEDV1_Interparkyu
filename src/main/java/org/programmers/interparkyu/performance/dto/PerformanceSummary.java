package org.programmers.interparkyu.performance.dto;

import org.programmers.interparkyu.performance.Performance;
import org.programmers.interparkyu.performance.PerformanceCategory;

public record PerformanceSummary(
    String title,
    PerformanceCategory category,
    Integer runtime
) {

    public static PerformanceSummary from(Performance performance) {
        return new PerformanceSummary(
            performance.getTitle(),
            performance.getCategory(),
            performance.getRuntime()
        );
    }
}
