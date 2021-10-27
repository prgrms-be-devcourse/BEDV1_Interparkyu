package org.programmers.interparkyu.performance.dto;

import org.programmers.interparkyu.performance.PerformanceCategory;

public record BriefPerformanceInfo(
    String title,
    PerformanceCategory category,
    String hallName
) {

}
