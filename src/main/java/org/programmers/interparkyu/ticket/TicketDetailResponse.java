package org.programmers.interparkyu.ticket;

import static org.programmers.interparkyu.utils.TimeUtil.dateFormatter;
import static org.programmers.interparkyu.utils.TimeUtil.performanceTimeFormatter;

import lombok.Builder;
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.performance.Round;

public record TicketDetailResponse(

    String ticketId,

    String paymentStatus,

    String section,

    Integer sectionSeatNumber,

    Integer price,

    String hallName,

    String title,

    Integer round,

    String date,

    String startTime

) {

  @Builder
  public  TicketDetailResponse {
  }

  public static TicketDetailResponse from(Ticket ticket){
    Seat seat = ticket.getSeat();
    Round round = ticket.getRound();

    return TicketDetailResponse.builder()
        .ticketId(ticket.getId())
        .paymentStatus(ticket.getPaymentStatus().toString())
        .section(seat.getSection().toString())
        .sectionSeatNumber(seat.getSectionSeatNumber())
        .price(seat.getPrice())
        .hallName(seat.getHallName())
        .title(round.getTitle())
        .round(round.getRound())
        .date(round.getDate().format(dateFormatter))
        .startTime(round.getStartTime().format(performanceTimeFormatter))
        .build();
  }
}
