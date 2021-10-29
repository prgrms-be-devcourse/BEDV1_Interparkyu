package org.programmers.interparkyu.performance.dto;

import org.programmers.interparkyu.performance.Performance;
import org.programmers.interparkyu.performance.PerformanceCategory;

public record BriefPerformanceInfo(
    String title,
    PerformanceCategory category,
    String hallName,
    String url
) {
    public static BriefPerformanceInfo from(Performance performance, String requestBaseUrl) {
        return new BriefPerformanceInfo(
            performance.getTitle(),
            performance.getCategory(),
            performance.getHall().getName(),
            String.format("%s/%s", requestBaseUrl, performance.getId())
        );
    }
}
