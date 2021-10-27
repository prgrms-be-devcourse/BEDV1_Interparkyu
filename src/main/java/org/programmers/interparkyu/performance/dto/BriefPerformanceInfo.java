package org.programmers.interparkyu.performance.dto;

import org.programmers.interparkyu.performance.PerformanceCategory;

public record BriefPerformanceInfo(
    String title,
    PerformanceCategory category,
    String hallName
) {

    public static BriefPerformanceInfo from(String title, PerformanceCategory category,
        String hallName) {
        return new BriefPerformanceInfo(title, category, hallName);
    }
}
