package org.programmers.interparkyu.performance.dto;

import static org.programmers.interparkyu.utils.TimeUtil.dateFormatter;

import lombok.Builder;
import org.programmers.interparkyu.performance.Round;

public record RoundDateResponse(
    String date
) {
    @Builder
    public RoundDateResponse { }

    public static RoundDateResponse from(Round round) {
        return RoundDateResponse.builder()
            .date(round.getDate().format(dateFormatter))
            .build();
    }
}
