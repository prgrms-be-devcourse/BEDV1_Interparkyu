package org.programmers.interparkyu.performance.dto.response;

import static org.programmers.interparkyu.common.utils.TimeUtil.dateFormatter;

import lombok.Builder;
import org.programmers.interparkyu.performance.domain.Round;

public record RoundDateResponse(String date) {

    @Builder
    public RoundDateResponse {}

    public static RoundDateResponse from(Round round) {
        return RoundDateResponse.builder()
            .date(round.getDate().format(dateFormatter))
            .build();
    }

}
