package org.programmers.interparkyu.performance.dto;


import java.util.List;
import org.programmers.interparkyu.performance.PerformanceCategory;

public record DetailPerformanceInfo(
    String title,
    PerformanceCategory category,
    Integer runtime,
    List<RoundInfo> roundInfo
) {

    public static DetailPerformanceInfo from(PerformanceSummary summary, List<RoundInfo> info) {
        return new DetailPerformanceInfo(
            summary.title(), summary.category(), summary.runtime(), info
        );
    }
}
