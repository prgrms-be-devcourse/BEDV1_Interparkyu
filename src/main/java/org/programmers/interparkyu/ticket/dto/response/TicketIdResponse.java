package org.programmers.interparkyu.ticket.dto.response;

public record TicketIdResponse(
    String ticketId
) {

    public static TicketIdResponse from(String id) {
        return new TicketIdResponse(id);
    }
}
