package org.programmers.interparkyu.ticket;

import java.text.MessageFormat;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class TicketController {

  private final TicketService ticketService;

  @GetMapping("/v1/tickets/{ticketId}")
  public ApiResponse<TicketDetailResponse> getReservationTicketDetail(final @PathVariable String ticketId){
    return ApiResponse.ok(
        MessageFormat.format("/v1/tickets/{0}", ticketId),
        ticketService.getReservationTicketDetail(ticketId)
    );
  }
}
