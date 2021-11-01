package org.programmers.interparkyu.performance.dto;

import static org.programmers.interparkyu.utils.TimeUtil.dateFormatter;
import static org.programmers.interparkyu.utils.TimeUtil.performanceTimeFormatter;
import static org.programmers.interparkyu.utils.TimeUtil.ticketingTimeFormatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import org.programmers.interparkyu.ReservationStatus;
import org.programmers.interparkyu.RoundSeat;
import org.programmers.interparkyu.performance.Round;

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

    private static Map<Integer, Map<String, Integer>> convertFrom(List<RoundSeat> roundSeats) {
        Map<Integer, Map<String, Integer>> map = new HashMap<>(); // < 회차번호 < 섹션이름, 좌석수 > >
        for (RoundSeat roundSeat : roundSeats) {
            Round round = roundSeat.getRound();
            map.putIfAbsent(round.getRound(), new HashMap<>());

            String sectionName = roundSeat.getSeat().getSection().toString();
            Map<String, Integer> sectionRemainingSeatCountMap = map.get(round.getRound());

            if (roundSeat.getReservationStatus().equals(ReservationStatus.NOT_RESERVED)) {
                sectionRemainingSeatCountMap.put(sectionName,
                    sectionRemainingSeatCountMap.getOrDefault(sectionName, 0) + 1
                );
            }
        }

        return map;
    }
}
