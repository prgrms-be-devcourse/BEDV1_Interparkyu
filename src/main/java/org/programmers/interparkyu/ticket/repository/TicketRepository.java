package org.programmers.interparkyu.ticket.repository;


import org.programmers.interparkyu.ticket.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, String> { }
