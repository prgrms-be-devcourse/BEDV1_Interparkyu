package org.programmers.interparkyu.performance.dto.response;

import static org.programmers.interparkyu.common.utils.RemainingSeatCalcConverter.convertFrom;
import static org.programmers.interparkyu.common.utils.TimeUtil.dateFormatter;
import static org.programmers.interparkyu.common.utils.TimeUtil.performanceTimeFormatter;
import static org.programmers.interparkyu.common.utils.TimeUtil.ticketingTimeFormatter;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import org.programmers.interparkyu.performance.domain.Round;
import org.programmers.interparkyu.ticket.domain.RoundSeat;

public record RoundResponse(

    String title,

    Integer round,

    String date,

    Integer remainingSeatsCount,

    String startTime,

    String endTime,

    String ticketingStartDateTime,

    String ticketingEndDateTime,

    String ticketCancelableUntil,

    String hall,

    Map<Integer, Map<String, Integer>> sectionRemainingSeatCount

) {

    @Builder
    public RoundResponse { }

    public static RoundResponse from(Round round, List<RoundSeat> roundSeats) {
        return RoundResponse.builder()
            .title(round.getTitle())
            .round(round.getRound())
            .date(round.getDate().format(dateFormatter))
            .remainingSeatsCount(round.getRemainingSeats())
            .startTime(round.getStartTime().format(performanceTimeFormatter))
            .endTime(round.getEndTime().format(performanceTimeFormatter))
            .ticketingStartDateTime(round.getTicketingStartDateTime().format(ticketingTimeFormatter))
            .ticketingEndDateTime(round.getTicketingEndDateTime().format(ticketingTimeFormatter))
            .ticketCancelableUntil(round.getTicketCancelableUntil().format(ticketingTimeFormatter))
            .hall(round.getPerformance().getHall().getName())
            .sectionRemainingSeatCount(convertFrom(roundSeats))
            .build();
    }

}
