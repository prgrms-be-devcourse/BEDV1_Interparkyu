package org.programmers.interparkyu.performance.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    @DisplayName("유저가 홈페이지 진입시 전체 공연 정보를 조회할 수 있다.")
    void getAllPerformaneInBrief() throws Exception {
        // TODO: 2021.10.28 TI-24 : 공연 추가 후 정상적으로 데이터가 받아와 지는지까지 확인해야 한다.
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
}