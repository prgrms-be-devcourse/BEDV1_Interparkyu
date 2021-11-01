package org.programmers.interparkyu.performance.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import org.programmers.interparkyu.performance.Performance;
import org.programmers.interparkyu.performance.dto.RoundInfo;
import org.programmers.interparkyu.performance.repository.UserPerformanceRepository;
import org.programmers.interparkyu.performance.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserPerformanceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPerformanceRepository userPerformanceRepository;

    @Autowired
    private RoundService roundService;

    @Test
    @DisplayName("유저가 홈페이지 진입시 전체 공연 정보를 조회할 수 있다.")
    void getAllPerformaneInBrief() throws Exception {
        // TODO: 2021.10.28 TI-25 : 테스트용 데이터를 넣고, 이를 가지고 테스트 하도록 수정해야 한다.
        //                          공연 정보 및 회차 등록 기능이 모두 구현되면 수정하기
        mockMvc.perform(get("/v1/performances")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.common.message")
            .value("success"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.common.requestUri")
            .value("/v1/performances"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.common.internalHttpStatusCode")
            .value(200))
        .andDo(print()); // 출력 포맷 확인
    }

    @Test
    @DisplayName("한 공연에 대한 상세 정보를 조회할 수 있다")
    void getDetailPerformanceInfo() throws Exception {
        // TODO: 2021.10.28 TI-25 : 테스트용 데이터를 넣고, 이를 가지고 테스트 하도록 수정해야 한다. -> 반드시 1개 이상의 데이터가 있음을 가정한 테스트이다.
        //                          공연 정보 및 회차 등록 기능이 모두 구현되면 수정하기
        List<Performance> performances = userPerformanceRepository.findAll();
        Performance performance = performances.get(0);
        List<RoundInfo> rounds = roundService.getAllRoundByPerformanceId(performance.getId());

        mockMvc.perform(get("/v1/performances/" + performance.getId())
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.common.message")
                .value("success"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.common.requestUri")
                .value("/v1/performances/" + performance.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.common.internalHttpStatusCode")
                .value(200))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.title")
                .value(performance.getTitle()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.category")
                .value(performance.getCategory().toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.runtime")
                .value(performance.getRuntime()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.roundInfo[0].round")
                .value(rounds.get(0).round()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.roundInfo[0].date")
                .value(rounds.get(0).date()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.roundInfo[0].startTime")
                .value(rounds.get(0).startTime()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.roundInfo[0].endTime")
                .value(rounds.get(0).endTime()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.roundInfo[0].ticketingStartDateTime")
                .value(rounds.get(0).ticketingStartDateTime()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.roundInfo[0].ticketingEndDateTime")
                .value(rounds.get(0).ticketingEndDateTime()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.roundInfo[0].ticketingCancelableUntil")
                .value(rounds.get(0).ticketingCancelableUntil()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.roundInfo[0].remainingSeats")
                .value(rounds.get(0).remainingSeats()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.roundInfo[0].hall")
                .value(rounds.get(0).hall()))
            .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 공연에 대한 상세 정보 요청에 NotFoundException이 정상적으로 반환된다.")
    void requestForWrongDetailPerformanceInfo() throws Exception {
        mockMvc.perform(get("/v1/performances/" + Integer.MAX_VALUE)
            .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk()) // 통신에는 성공했으므로 기본적으로는 200으로 응답이 돌아온다.
            .andExpect(MockMvcResultMatchers.jsonPath("$.common.message")
                .value("No such performance exist")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.common.internalHttpStatusCode")
                .value(404)
            )
            .andDo(print());
    }
}
