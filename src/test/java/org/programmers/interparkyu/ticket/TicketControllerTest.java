package org.programmers.interparkyu.ticket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.programmers.interparkyu.common.utils.TimeUtil.dateFormatter;
import static org.programmers.interparkyu.common.utils.TimeUtil.performanceTimeFormatter;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.interparkyu.BaseControllerTest;
import org.programmers.interparkyu.hall.domain.Seat;
import org.programmers.interparkyu.performance.domain.Round;
import org.programmers.interparkyu.performance.repository.RoundRepository;
import org.programmers.interparkyu.ticket.domain.PaymentStatus;
import org.programmers.interparkyu.ticket.domain.ReservationStatus;
import org.programmers.interparkyu.ticket.domain.RoundSeat;
import org.programmers.interparkyu.ticket.domain.Ticket;
import org.programmers.interparkyu.ticket.dto.request.CreateTicketRequest;
import org.programmers.interparkyu.ticket.repository.TicketRepository;
import org.programmers.interparkyu.ticket.service.RoundSeatService;
import org.programmers.interparkyu.ticket.service.TicketService;
import org.programmers.interparkyu.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
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
    private RoundRepository roundRepository;

    @Autowired
    private RoundSeatService roundSeatService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("티켓 1개 정보를 가져올 수 있다.")
    public void getReservationTicketDetail() throws Exception {
        // Given
        Seat givenSeat = givenTicket.getSeat();
        Round givenRound = givenTicket.getRound();

        // When Then
        mockMvc
            .perform(get("/v1/tickets/" + givenTicket.getId()))
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.common.internalHttpStatusCode")
                    .value(200)
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data.ticketId")
                    .value(givenTicket.getId())
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data.section")
                    .value(givenSeat.getSection().toString())
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data.sectionSeatNumber")
                    .value(givenSeat.getSectionSeatNumber())
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data.price")
                    .value(givenSeat.getPrice())
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data.hallName")
                    .value(givenSeat.getHallName())
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data.title")
                    .value(givenRound.getTitle())
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data.round")
                    .value(givenRound.getRound())
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data.date")
                    .value(givenRound.getDate().format(dateFormatter))
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data.startTime")
                    .value(givenRound.getStartTime().format(performanceTimeFormatter))
            );
    }

    @Test
    @DisplayName("티켓을 생성할 수 있다")
    @Transactional
    public void testCreateTicket() throws Exception {
        // Given
        Long roundSeatId = givenUnReservedRoundSeat.getId();
        Long userId = givenUser.getId();
        CreateTicketRequest request = new CreateTicketRequest(roundSeatId, userId);
        int remainingSeatBefore = roundRepository.getById(givenRound.getId()).getRemainingSeats();

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

        int remainingSeatAfter = roundRepository.getById(givenRound.getId()).getRemainingSeats();
        assertThat(remainingSeatBefore, equalTo(10));
        assertThat(remainingSeatBefore - 1, equalTo(remainingSeatAfter));
    }

    @Test
    @Transactional
    @DisplayName("좌석 선택 동시성 테스트(낙관락)")
    void getBoard() {
        //given
        CountDownLatch latch = new CountDownLatch(100);
        ExecutorService service = Executors.newFixedThreadPool(100);
        int remainingSeatBefore = roundRepository.getById(givenRound.getId()).getRemainingSeats();

        Long roundSeatId = givenUnReservedRoundSeat.getId();
        Long userId = givenUser.getId();
        CreateTicketRequest request = new CreateTicketRequest(roundSeatId, userId);

        //when
        for (int i = 0; i < 100; i++) {
            System.out.println("------------");
            System.out.println(i);

            service.execute(()->{
                try {
                    MvcResult result = mockMvc
                        .perform(
                            post("/v1/tickets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                        )
//                        .andExpect(
//                            MockMvcResultMatchers
//                                .jsonPath("$.common.internalHttpStatusCode")
//                                .value(200)
//                        )
                        .andReturn();

                } catch (ObjectOptimisticLockingFailureException lockingFailureException) {
                    System.out.println("충돌!");
                    // 예외를 잡아 복구작업을 해주면됩니다.
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            latch.countDown();
        }

        try {
            latch.await();  // wait until latch counted down to 0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int remainingSeatAfter = roundRepository.getById(givenRound.getId()).getRemainingSeats();
        assertThat(remainingSeatBefore, equalTo(10));
        assertThat(remainingSeatBefore - 1, equalTo(remainingSeatAfter));
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
