package org.programmers.interparkyu.ticket;

import java.text.MessageFormat;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.RoundSeat;
import org.programmers.interparkyu.error.exception.NotFoundException;
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.hall.service.SeatService;
import org.programmers.interparkyu.performance.Round;
import org.programmers.interparkyu.roundSeat.RoundSeatService;
import org.programmers.interparkyu.user.UserRepository;
import org.programmers.interparkyu.user.UserService;
import org.programmers.interparkyu.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TicketService {

  private final TicketRepository ticketRepository;

  private final UserService userService;

  private final SeatService seatService;

  private final RoundSeatService roundSeatService;

  @Transactional(readOnly = true)
  public TicketDetailResponse getReservationTicketDetail(final String ticketId){
    Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException(
        MessageFormat.format("ticketId : {0}, 해당 티켓을 찾을 수 없습니다.", ticketId)));

    return TicketDetailResponse.from(ticket);
  }

  @Transactional
  public TicketIdResponse createTicket(CreateTicketRequest request) {
    RoundSeat roundSeat = roundSeatService.getRoundSeatById(request.roundSeatId());
    Round round = roundSeat.getRound();
    Seat seat = roundSeat.getSeat();
    User user = userService.getUserById(request.userId());

    Ticket ticket = new Ticket();
    ticket.setRound(round);
    ticket.setSeat(seat);
    ticket.setUser(user);
    ticketRepository.save(ticket);

    return TicketIdResponse.from(ticket.getId());
  }
}
