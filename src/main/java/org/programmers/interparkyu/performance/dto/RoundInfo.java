package org.programmers.interparkyu.performance.dto;

import java.time.format.DateTimeFormatter;
import lombok.Builder;
import org.programmers.interparkyu.performance.Round;

public record RoundInfo(
    Integer round,
    String date,
    String startTime,
    String endTime,
    String ticketingStartDateTime,
    String ticketingEndDateTime,
    String ticketingCancelableUntil,
    Integer remainingSeats,
    String hall
) {

    @Builder
    public RoundInfo { }

    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static DateTimeFormatter performanceTimeFormatter = DateTimeFormatter.ofPattern("HHmm");

    public static DateTimeFormatter ticketingTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMssHHmm");

    public static RoundInfo from(Round round) {
        return RoundInfo.builder()
            .round(round.getRound())
            .date(round.getDate().format(dateFormatter))
            .startTime(round.getStartTime().format(performanceTimeFormatter))
            .endTime(round.getEndTime().format(performanceTimeFormatter))
            .ticketingStartDateTime(round.getTicketingStartDateTime().format(ticketingTimeFormatter))
            .ticketingEndDateTime(round.getTicketingEndDateTime().format(ticketingTimeFormatter))
            .ticketingCancelableUntil(round.getTicketCancelableUntil().format(ticketingTimeFormatter))
            .remainingSeats(round.getRemainingSeats())
            .hall(round.getPerformance().getHall().getName())
            .build();
    }
}
