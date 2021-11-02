package org.programmers.interparkyu.ticket.dto.request;

import javax.validation.constraints.Positive;

public record CreateTicketRequest(

    @Positive
    Long roundSeatId,

    @Positive
    Long userId

) {}
