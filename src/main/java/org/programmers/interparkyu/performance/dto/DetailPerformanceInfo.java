package org.programmers.interparkyu.performance.dto;


import java.util.List;
import org.programmers.interparkyu.performance.PerformanceCategory;

public record DetailPerformanceInfo(
    String title,
    PerformanceCategory category,
    Integer runtime,
    List<RoundDateResponse> roundDate
) {

    public static DetailPerformanceInfo from(PerformanceSummary summary, List<RoundDateResponse> date) {
        return new DetailPerformanceInfo(summary.title(), summary.category(), summary.runtime(), date);
    }
}
