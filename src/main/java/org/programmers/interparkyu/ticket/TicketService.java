package org.programmers.interparkyu.ticket;

import java.text.MessageFormat;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.error.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TicketService {

  private final TicketRepository ticketRepository;

  @Transactional(readOnly = true)
  public TicketDetailResponse getReservationTicketDetail(final String ticketId){
    Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException(
        MessageFormat.format("ticketId : {0}, 해당 티켓을 찾을 수 없습니다.", ticketId)));

    return TicketDetailResponse.from(ticket);
  }
}
