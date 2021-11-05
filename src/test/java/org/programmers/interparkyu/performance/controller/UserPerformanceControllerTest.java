package org.programmers.interparkyu.performance.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.interparkyu.BaseControllerTest;
import org.programmers.interparkyu.performance.domain.Performance;
import org.programmers.interparkyu.performance.dto.response.RoundDateResponse;
import org.programmers.interparkyu.performance.repository.PerformanceRepository;
import org.programmers.interparkyu.performance.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class UserPerformanceControllerTest extends BaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private RoundService roundService;

    @Test
    @DisplayName("유저가 홈페이지 진입시 전체 공연 정보를 조회할 수 있다.")
    void getAllPerformaneInBrief() throws Exception {
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
            .andDo(print());
    }

    @Test
    @DisplayName("한 공연에 대한 상세 정보를 조회할 수 있다 -> 간단하게 표시할 공연 정보와, 공연이 있는 날짜만 내려준다")
    void getDetailPerformanceInfo() throws Exception {
        List<Performance> performances = performanceRepository.findAll();
        Performance performance = performances.get(0);
        List<RoundDateResponse> roundDates = roundService.getAll(performance.getId());

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
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.roundDate[0].date")
                .value(roundDates.get(0).date()))
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
                .isString()
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.common.internalHttpStatusCode")
                .value(404)
            )
            .andDo(print());
    }

}