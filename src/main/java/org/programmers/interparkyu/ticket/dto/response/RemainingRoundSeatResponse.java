package org.programmers.interparkyu.ticket.dto.response;

import lombok.Builder;
import org.programmers.interparkyu.hall.domain.Seat;
import org.programmers.interparkyu.hall.domain.Section;
import org.programmers.interparkyu.ticket.domain.ReservationStatus;
import org.programmers.interparkyu.ticket.domain.RoundSeat;

public record RemainingRoundSeatResponse(

    Long id,    // 회차 좌석 ID

    ReservationStatus reservationStatus,

    Integer round,      // 몇 회차인지

    Long seatId,

    Section section,

    Integer sectionSeatNumber,

    Integer price

) {

    @Builder
    public RemainingRoundSeatResponse { }

    public static RemainingRoundSeatResponse from(Seat seat, RoundSeat roundSeat) {
        return RemainingRoundSeatResponse.builder()
            .id(roundSeat.getId())
            .reservationStatus(roundSeat.getReservationStatus())
            .round(roundSeat.getRound().getRound())
            .seatId(seat.getId())
            .section(seat.getSection())
            .sectionSeatNumber(seat.getSectionSeatNumber())
            .price(seat.getPrice())
            .build();
    }

}
