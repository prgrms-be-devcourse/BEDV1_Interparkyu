package org.programmers.interparkyu.performance.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.interparkyu.performance.UserPerformanceRepository;
import org.programmers.interparkyu.performance.UserPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserPerformanceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserPerformanceRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("유저가 홈페이지 진입시 전체 공연 정보를 조회할 수 있다.")
    void getAllPerformaneInBrief() throws Exception {
        mockMvc.perform(get("/v1/performances")
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andDo(print());
    }
}