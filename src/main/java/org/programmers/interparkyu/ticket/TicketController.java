package org.programmers.interparkyu.ticket;

import static org.programmers.interparkyu.ticket.TicketController.ticketBaseUri;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(ticketBaseUri)
public class TicketController {

  public static final String ticketBaseUri = "/v1/tickets";

  private final TicketService ticketService;

  @GetMapping("/{ticketId}")
  public ApiResponse<TicketDetailResponse> getReservationTicketDetail(final @PathVariable String ticketId){
    return ApiResponse.ok(
        ticketBaseUri + ticketId,
        ticketService.getReservationTicketDetail(ticketId)
    );
  }

  @PostMapping
  public ApiResponse<TicketIdResponse> createTicket(@Valid @RequestBody CreateTicketRequest request) {
    return ApiResponse.ok(ticketBaseUri, ticketService.createTicket(request));
  }

  @PatchMapping("/{ticketId}/paymentStatus/completed")
  public ApiResponse<Object> completeTicketPayment(final @PathVariable String ticketId) {
    ticketService.completeTicketPayment(ticketId);
    return ApiResponse.ok(ticketBaseUri + ticketId + "/paymentStatus/completed");
  }
}
