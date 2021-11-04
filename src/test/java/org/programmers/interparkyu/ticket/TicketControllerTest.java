package org.programmers.interparkyu.ticket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.interparkyu.BaseControllerTest;
import org.programmers.interparkyu.ticket.domain.PaymentStatus;
import org.programmers.interparkyu.ticket.domain.ReservationStatus;
import org.programmers.interparkyu.ticket.domain.RoundSeat;
import org.programmers.interparkyu.ticket.domain.Ticket;
import org.programmers.interparkyu.ticket.dto.request.CreateTicketRequest;
import org.programmers.interparkyu.ticket.repository.TicketRepository;
import org.programmers.interparkyu.ticket.service.RoundSeatService;
import org.programmers.interparkyu.ticket.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

class TicketControllerTest extends BaseControllerTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RoundSeatService roundSeatService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("티켓 1개 정보를 가져올 수 있다.")
    public void getReservationTicketDetail() throws Exception {

        //Todo ticket create Api 만들어진 후에 작성 필요

    }

    @Test
    @DisplayName("티켓을 생성할 수 있다")
    @Transactional
    public void testCreateTicket() throws Exception {
        // Given
        Long roundSeatId = givenUnReservedRoundSeat.getId();
        Long userId = givenUser.getId();
        CreateTicketRequest request = new CreateTicketRequest(roundSeatId, userId);

        // When
        MvcResult result = mockMvc
            .perform(
                post("/v1/tickets")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.common.internalHttpStatusCode")
                    .value(200)
            )
            .andReturn();

        // Then
        String ticketId = JsonPath
            .parse(result.getResponse().getContentAsString())
            .read("$.data.ticketId", String.class);

        ticketService.getReservationTicketDetail(ticketId);

        RoundSeat roundSeat = roundSeatService.getRoundSeat(roundSeatId);
        assertThat(
            roundSeat.getReservationStatus(), equalTo(ReservationStatus.WAITING_FOR_PAYMENT));
    }

    @Test
    @DisplayName("티켓을 결제 완료할 수 있다")
    @Transactional
    public void testTicketPaymentComplete() throws Exception {
        // Given
        String ticketId = givenTicket.getId();
        Long roundSeatId = givenReservedRoundSeat.getId();

        // When
        mockMvc.perform(patch("/v1/tickets/" + ticketId + "/paymentStatus/completed"));

        // Then
        Ticket ticket = ticketRepository.getById(ticketId);
        assertThat(ticket.getPaymentStatus(), equalTo(PaymentStatus.COMPLETED));

        RoundSeat roundSeat = roundSeatService.getRoundSeat(roundSeatId);
        assertThat(roundSeat.getReservationStatus(), equalTo(ReservationStatus.RESERVED));
    }

}
