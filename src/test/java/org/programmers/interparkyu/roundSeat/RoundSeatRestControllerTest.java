package org.programmers.interparkyu.roundSeat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.programmers.interparkyu.ReservationStatus;
import org.programmers.interparkyu.performance.Performance;
import org.programmers.interparkyu.performance.Round;
import org.programmers.interparkyu.performance.dto.RoundDateResponse;
import org.programmers.interparkyu.performance.dto.RoundInfo;
import org.programmers.interparkyu.performance.repository.RoundRepository;
import org.programmers.interparkyu.performance.repository.UserPerformanceRepository;
import org.programmers.interparkyu.performance.service.RoundService;
import org.programmers.interparkyu.roundSeat.dto.RoundSeatResponse;
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
class RoundSeatRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPerformanceRepository userPerformanceRepository;

    @Autowired
    private RoundService roundService;

    @Autowired
    private RoundSeatService roundSeatService;

    @Test
    @DisplayName("어떤 공연의 한 회차가 선택되었을 때, 해당 회차의 좌석 정보를 정상적으로 받아올 수 있다")
    void getAllRoundSeatsOfRoundOfPerformance() throws Exception {
        List<Performance> performances = userPerformanceRepository.findAll();
        Performance performance = performances.get(0);
        List<RoundDateResponse> rounds = roundService.getAllRoundByPerformanceId(performance.getId());
        // TODO 2021.10.30 TI-26 : 여기서 회차의 ID를 받아올 수 있어야 한다. 이후 추가하면 rounds에서 하나의 회차를 골라 그 ID를 사용하도록 수정한다.
        //                         현재는 ID를 받아오지 않으므로 임의로 직접 설정해준다.
        //                         이후 테스트 메서드 내에서 값을 넣어줄 수 있으면 검증하는 값들을 정확하게 확인하도록 수정하자.

        mockMvc.perform(get("/v1/roundSeats/5") // 임의로 "삶은계란구운계란" 공연의 4회차 공연을 선택
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id")
                .isNumber()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].reservationStatus")
                .isString()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].seatId")
                .isNumber()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].section")
                .isString()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].sectionSeatNumber")
                .isNumber()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].price")
                .isNumber()
            )
            .andDo(print());
    }

}
