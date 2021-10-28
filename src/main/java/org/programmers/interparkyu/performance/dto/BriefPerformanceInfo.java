package org.programmers.interparkyu.performance.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.programmers.interparkyu.performance.Performance;
import org.programmers.interparkyu.performance.PerformanceCategory;

public record BriefPerformanceInfo(
    String title,
    PerformanceCategory category,
    String hallName
) {
    public static BriefPerformanceInfo from(Performance performance) {
        return new BriefPerformanceInfo(
            performance.getTitle(),
            performance.getCategory(),
            performance.getHall().getName()
        );
    }
}