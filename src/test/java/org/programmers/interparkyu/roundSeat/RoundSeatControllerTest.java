package org.programmers.interparkyu.roundSeat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.programmers.interparkyu.performance.repository.UserPerformanceRepository;
import org.programmers.interparkyu.performance.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class RoundSeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPerformanceRepository userPerformanceRepository;

    @Autowired
    private RoundService roundService;

    @Autowired
    private RoundSeatService roundSeatService;

    @Test
    @DisplayName("어떤 공연과 날짜가 선택되었을 때, 해당 날짜에 상연하는 각 회차의 잔여 회차좌석 수 를 보내준다.")
    void getAllRoundSeatsOfRoundOfPerformance() throws Exception {
        mockMvc.perform(
            get("/v1/roundSeats/19") // 임의로 "꽃다람쥐" 공연의 2022년 1월 10일자 공연을 선택
                .contentType(MediaType.APPLICATION_JSON)
                .param( "date", "20220110") // 1회차와 2회차가 상연된다.
            )
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title")
                .value("꽃다람쥐")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].round")
                .value(1)
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].date")
                .isString()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].remainingSeatsCount")
                .isNumber()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].startTime")
                .isString()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].endTime")
                .isString()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].ticketingStartDateTime")
                .isString()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].ticketingEndDateTime")
                .isString()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].ticketCancelableUntil")
                .isString()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].hall")
                .isString()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].sectionRemainingSeatCount")
                .isMap()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].sectionRemainingSeatCount.1")
                .isMap()
            )
            .andDo(print());
    }

}
