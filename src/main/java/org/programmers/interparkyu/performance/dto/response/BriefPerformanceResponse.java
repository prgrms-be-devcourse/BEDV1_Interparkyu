package org.programmers.interparkyu.performance.dto.response;

import java.text.MessageFormat;
import lombok.Builder;
import org.programmers.interparkyu.performance.domain.Performance;
import org.programmers.interparkyu.performance.domain.PerformanceCategory;

public record BriefPerformanceResponse(

    String title,

    PerformanceCategory category,

    String hallName,

    String url

) {

    @Builder
    public BriefPerformanceResponse {}

    public static BriefPerformanceResponse from(Performance performance, String requestBaseUrl) {
        return BriefPerformanceResponse.builder()
            .title(performance.getTitle())
            .category(performance.getCategory())
            .hallName(performance.getHall().getName())
            .url(MessageFormat.format("{0}/{1}", requestBaseUrl, performance.getId()))
            .build();
    }

}
