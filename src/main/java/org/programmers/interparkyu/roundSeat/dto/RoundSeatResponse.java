package org.programmers.interparkyu.roundSeat.dto;

import static org.programmers.interparkyu.utils.RemainingSeatCalcConverter.convertFrom;
import static org.programmers.interparkyu.utils.TimeUtil.dateFormatter;
import static org.programmers.interparkyu.utils.TimeUtil.performanceTimeFormatter;
import static org.programmers.interparkyu.utils.TimeUtil.ticketingTimeFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import org.programmers.interparkyu.ReservationStatus;
import org.programmers.interparkyu.RoundSeat;
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.performance.Round;

public record RoundSeatResponse(
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

    Map<Integer, Map<String, Integer>> sectionRemainingSeatCount,

    List<RemainingRoundSeatResponse> roundSeats
) {
    @Builder
    public RoundSeatResponse { }

    public static RoundSeatResponse from(Round round, List<RoundSeat> roundSeats) {
        return RoundSeatResponse.builder()
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
            .roundSeats(roundSeats.stream().map(roundSeat -> {
                Seat seat = roundSeat.getSeat();
                return RemainingRoundSeatResponse.from(seat, roundSeat);
            }).toList())
            .build();
    }
}
