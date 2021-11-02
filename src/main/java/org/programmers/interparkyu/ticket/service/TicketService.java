package org.programmers.interparkyu.ticket.service;

import java.text.MessageFormat;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.common.error.exception.NotFoundException;
import org.programmers.interparkyu.hall.domain.Seat;
import org.programmers.interparkyu.performance.domain.Round;
import org.programmers.interparkyu.ticket.domain.RoundSeat;
import org.programmers.interparkyu.ticket.domain.Ticket;
import org.programmers.interparkyu.ticket.dto.request.CreateTicketRequest;
import org.programmers.interparkyu.ticket.dto.response.TicketDetailResponse;
import org.programmers.interparkyu.ticket.dto.response.TicketIdResponse;
import org.programmers.interparkyu.ticket.repository.TicketRepository;
import org.programmers.interparkyu.user.domain.User;
import org.programmers.interparkyu.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    private final UserService userService;

    private final RoundSeatService roundSeatService;

    @Transactional(readOnly = true)
    public TicketDetailResponse getReservationTicketDetail(final String ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new NotFoundException(
            MessageFormat.format("ticketId : {0}, 해당 티켓을 찾을 수 없습니다.", ticketId)));

        return TicketDetailResponse.from(ticket);
    }

    @Transactional
    public TicketIdResponse createTicket(CreateTicketRequest request) {
        RoundSeat roundSeat = roundSeatService.getRoundSeatById(request.roundSeatId());
        // TODO 2021-11-02 동기화 문제에 대해 더 고민이 필요함
        roundSeat.waitForPayment();

        Round round = roundSeat.getRound();
        Seat seat = roundSeat.getSeat();
        User user = userService.getUser(request.userId());

        Ticket ticket = new Ticket();
        ticket.setRound(round);
        ticket.setSeat(seat);
        ticket.setUser(user);
        ticketRepository.save(ticket);

        return TicketIdResponse.from(ticket.getId());
    }

    @Transactional
    public void completeTicketPayment(String ticketId) {
        Ticket ticket = this.getTicket(ticketId);
        ticket.complete();

        RoundSeat roundSeat = roundSeatService.getRoundSeat(ticket.getRound(), ticket.getSeat());
        roundSeat.reserve();
    }

    private Ticket getTicket(String ticketId) {
        return ticketRepository.findById(ticketId)
            .orElseThrow(() -> new NotFoundException(
                MessageFormat.format("ticket with id {0} not found", ticketId)));
    }

}
