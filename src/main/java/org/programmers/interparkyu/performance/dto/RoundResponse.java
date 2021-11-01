package org.programmers.interparkyu.performance.dto;

import static org.programmers.interparkyu.utils.TimeUtil.dateFormatter;
import static org.programmers.interparkyu.utils.TimeUtil.performanceTimeFormatter;
import static org.programmers.interparkyu.utils.TimeUtil.ticketingTimeFormatter;

import java.util.List;
import lombok.Builder;
import org.programmers.interparkyu.RoundSeat;
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.performance.Round;
import org.programmers.interparkyu.roundSeat.dto.RoundSeatResponse;

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

    List<RoundSeatResponse> roundSeats
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
            .roundSeats(
                roundSeats.stream()
                    .map(roundSeat -> {
                        Seat seat = roundSeat.getSeat();
                        return RoundSeatResponse.from(seat, roundSeat);
                    }).toList()
            )
            .build();
    }

}
