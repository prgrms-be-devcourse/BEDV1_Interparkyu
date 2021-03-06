package org.programmers.interparkyu.ticket;

import static org.programmers.interparkyu.common.utils.TimeUtil.performanceTimeFormatter;
import static org.programmers.interparkyu.common.utils.TimeUtil.ticketingTimeFormatter;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.interparkyu.BaseControllerTest;
import org.programmers.interparkyu.performance.domain.Performance;
import org.programmers.interparkyu.performance.repository.PerformanceRepository;
import org.programmers.interparkyu.performance.service.RoundService;
import org.programmers.interparkyu.ticket.dto.response.RoundSeatResponse;
import org.programmers.interparkyu.ticket.service.RoundSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class RoundSeatControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private RoundService roundService;

    @Autowired
    private RoundSeatService roundSeatService;

    @Test
    @DisplayName("어떤 공연과 날짜가 선택되었을 때, 해당 날짜에 상연하는 각 회차의 잔여 회차좌석 수 를 보내준다.")
    void getAllRoundSeatsOfRoundOfPerformance() throws Exception {
        Performance performance = givenPerformance;
        Long performanceId = performance.getId();
        Integer roundNumber = givenRound.getRound();
        String date = givenRound.getDate().toString();

        mockMvc.perform(get("/v1/performances/" + performance.getId() + "/round")
                .contentType(MediaType.APPLICATION_JSON)
                .param("date", date.replace("-", ""))
            )
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title")
                .value(performance.getTitle())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].round")
                .value(roundNumber)
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].date")
                .value(date.replace("-", ""))
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].remainingSeatsCount")
                .value(givenRound.getRemainingSeats())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].startTime")
                .value(givenRound.getStartTime().format(performanceTimeFormatter))
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].endTime")
                .value(givenRound.getEndTime().format(performanceTimeFormatter))
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].ticketingStartDateTime")
                .value(givenRound.getTicketingStartDateTime().format(ticketingTimeFormatter))
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].ticketingEndDateTime")
                .value(givenRound.getTicketingEndDateTime().format(ticketingTimeFormatter))
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].ticketCancelableUntil")
                .value(givenRound.getTicketCancelableUntil().format(ticketingTimeFormatter))
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].hall")
                .value(performance.getHall().getName())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].sectionRemainingSeatCount")
                .isMap()
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.data[0].sectionRemainingSeatCount." + roundNumber)
                    .isMap()
            )
            .andDo(print());
    }

    @Test
    @DisplayName("공연, 날짜, 회차가 선택되었을 때 해당 회차의 모든 회차 좌석 정보를 받아온다.")
    void getAllRoundSeatWhenGivenPerformanceAndDateAndRound() throws Exception {
        Performance performance = givenPerformance;
        Long performanceId = performance.getId();

        Integer roundNumber = givenRound.getRound();
        String date = givenRound.getDate().toString();

        List<RoundSeatResponse> roundSeats = roundSeatService.getAllRoundSeat(
            performanceId, date.replace("-", ""), roundNumber);
        RoundSeatResponse roundSeatResponse = roundSeats.get(0);

        mockMvc.perform(
                get("/v1/performances/" + performanceId + "/round/" + roundNumber
                    + " /seats") // 임의로 "꽃다람쥐" 공연의 2022년 1월 10일자 1회차 공연을 선택
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("date", date.replace("-", ""))
            )
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title")
                .value(performance.getTitle())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].round")
                .value(roundNumber)
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].date")
                .value(date.replace("-", ""))
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].remainingSeatsCount")
                .value(roundSeatResponse.remainingSeatsCount())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].startTime")
                .value(roundSeatResponse.startTime())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].endTime")
                .value(roundSeatResponse.endTime())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].ticketingStartDateTime")
                .value(roundSeatResponse.ticketingStartDateTime())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].ticketingEndDateTime")
                .value(roundSeatResponse.ticketingEndDateTime())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].ticketCancelableUntil")
                .value(roundSeatResponse.ticketCancelableUntil())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].hall")
                .value(performance.getHall().getName())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].sectionRemainingSeatCount")
                .isMap()
            )
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.data[0].sectionRemainingSeatCount." + roundNumber)
                    .isMap()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].roundSeats")
                .isArray()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].roundSeats[0].id") // roundSeatId
                .value(roundSeatResponse.roundSeats().get(0).id())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].roundSeats[0].reservationStatus")
                .value(roundSeatResponse.roundSeats().get(0).reservationStatus().toString())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].roundSeats[0].round")
                .value(roundSeatResponse.roundSeats().get(0).round())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].roundSeats[0].seatId")
                .value(roundSeatResponse.roundSeats().get(0).seatId())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].roundSeats[0].section")
                .value(roundSeatResponse.roundSeats().get(0).section().toString())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].roundSeats[0].sectionSeatNumber")
                .value(roundSeatResponse.roundSeats().get(0).sectionSeatNumber())
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].roundSeats[0].price")
                .value(roundSeatResponse.roundSeats().get(0).price())
            )
            .andDo(print());
    }

}
