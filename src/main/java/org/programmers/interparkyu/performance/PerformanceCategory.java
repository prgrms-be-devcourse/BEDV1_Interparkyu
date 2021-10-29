package org.programmers.interparkyu.performance;

public enum PerformanceCategory {
    MUSICAL,
    CONCERT,
    THEATER,
    CLASSIC,
    DANCE;

    public static PerformanceCategory of(String performanceCategory){
        return PerformanceCategory.valueOf(performanceCategory);
    }
}
