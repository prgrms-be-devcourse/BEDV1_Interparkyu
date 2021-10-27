package org.programmers.interparkyu.performance;

import org.programmers.interparkyu.performance.dto.BriefPerformanceInfo;
import org.springframework.stereotype.Component;

@Component
public class PerformanceConverter {
    public BriefPerformanceInfo convertToBriefPerformanceInfo(Performance performance) {
        return new BriefPerformanceInfo(
            performance.getTitle(), performance.getCategory(), performance.getHall().getName());
    }
}
