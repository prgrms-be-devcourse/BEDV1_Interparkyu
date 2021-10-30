package org.programmers.interparkyu.roundSeat.dto;

import lombok.Builder;
import org.programmers.interparkyu.ReservationStatus;
import org.programmers.interparkyu.RoundSeat;
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.hall.Section;

public record RoundSeatResponse(
    Long id,
    ReservationStatus reservationStatus,
    Long seatId,
    Section section,
    Integer sectionSeatNumber,
    Integer price
) {
    @Builder
    public RoundSeatResponse { }

    public static RoundSeatResponse from(Seat seat, RoundSeat roundSeat) {
        return RoundSeatResponse.builder()
            .id(roundSeat.getId())
            .reservationStatus(roundSeat.getReservationStatus())
            .seatId(seat.getId())
            .section(seat.getSection())
            .sectionSeatNumber(seat.getSectionSeatNumber())
            .price(seat.getPrice())
            .build();
    }
}
