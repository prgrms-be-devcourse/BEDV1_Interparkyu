package org.programmers.interparkyu.hall.dto;

import lombok.Builder;
import org.programmers.interparkyu.hall.domain.Seat;
import org.programmers.interparkyu.hall.domain.Section;

public record SeatResponse(
    Long seatId,
    Section section,
    Integer sectionSeatNumber,
    Integer price
) {
    @Builder
    public SeatResponse { }

    public static SeatResponse from(Seat seat) {
        return SeatResponse.builder()
            .seatId(seat.getId())
            .section(seat.getSection())
            .sectionSeatNumber(seat.getSectionSeatNumber())
            .price(seat.getPrice())
            .build();
    }
}
