package org.programmers.interparkyu.ticket;

import javax.validation.constraints.Positive;

public record CreateTicketRequest(

    @Positive
    Long roundSeatId,

    @Positive
    Long userId

) {

}
