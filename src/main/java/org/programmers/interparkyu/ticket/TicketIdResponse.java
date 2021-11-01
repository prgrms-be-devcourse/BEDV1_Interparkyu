package org.programmers.interparkyu.ticket;

public record TicketIdResponse(
    String ticketId
) {

    public static TicketIdResponse from(String id) {
        return new TicketIdResponse(id);
    }
}
