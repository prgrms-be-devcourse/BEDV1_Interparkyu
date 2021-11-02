package org.programmers.interparkyu.performance.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.programmers.interparkyu.performance.controller.AdminPerformanceController.performanceRequestUri;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.interparkyu.hall.domain.Hall;
import org.programmers.interparkyu.hall.repository.HallRepository;
import org.programmers.interparkyu.performance.domain.Performance;
import org.programmers.interparkyu.performance.dto.request.PerformanceCreateRequest;
import org.programmers.interparkyu.performance.dto.request.PerformanceModifyRequest;
import org.programmers.interparkyu.performance.dto.response.PerformanceCreateResponse;
import org.programmers.interparkyu.performance.service.AdminPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class AdminPerformanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private AdminPerformanceService adminPerformanceService;

    @Test
    @DisplayName("공연 정보를 등록할 수 있다.")
    public void savePerformanceTest() throws Exception {
        // Given
        Hall hall = Hall.builder()
            .name("올림픽홀")
            .seatCount(300)
            .build();

        hallRepository.save(hall);

        PerformanceCreateRequest request = new PerformanceCreateRequest(
            "방탄소년단", "20301010", "20301110",
            "180", "CONCERT", "올림픽홀"
        );

        // When Then
        MvcResult result = mockMvc
            .perform(
                post(performanceRequestUri)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.common.internalHttpStatusCode")
                    .value(200)
            )
            .andReturn();

        Long id = JsonPath
            .parse(result.getResponse().getContentAsString())
            .read("$.data.id", Long.class);

        Performance performance = adminPerformanceService.findPerformance(id);
        assertThat(request.title(), equalTo(performance.getTitle()));
    }

    @Test
    @DisplayName("공연 정보를 수정할 수 있다.")
    public void modifyPerformanceTest() throws Exception {
        // Given
        Hall hall = Hall.builder()
            .name("올림픽홀1")
            .seatCount(300)
            .build();

        hallRepository.save(hall);

        PerformanceCreateRequest createRequest = new PerformanceCreateRequest(
            "흥탄소년단",
            "20301010",
            "20301110",
            "180",
            "CONCERT",
            "올림픽홀1"
        );

        PerformanceCreateResponse createResponse = adminPerformanceService.createPerformance(
            createRequest);

        PerformanceModifyRequest modifyRequest = new PerformanceModifyRequest(
            "흥탄소녀단",
            "20301010",
            "20401110",
            "180",
            "CONCERT",
            "올림픽홀1"
        );

        // When Then
        mockMvc
            .perform(
                put("/admin/v1/performances/" + createResponse.id())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(modifyRequest))
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.common.internalHttpStatusCode")
                    .value(200)
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.data.id")
                    .value(createResponse.id())
            );
    }

    @Test
    @DisplayName("공연 정보를 삭제할 수 있다.")
    public void deletePerformanceTest() throws Exception {
        // Given
        Hall hall = Hall.builder()
            .name("올림픽홀2")
            .seatCount(300)
            .build();

        hallRepository.save(hall);

        PerformanceCreateRequest request = new PerformanceCreateRequest(
            "방탄소년단", "20301010", "20301110", "180", "CONCERT", "올림픽홀2");

        // When Then
        mockMvc
            .perform(
                delete(
                    "/admin/v1/performances/" + adminPerformanceService.createPerformance(request)
                        .id())
            )
            .andExpect(
                MockMvcResultMatchers
                    .jsonPath("$.common.internalHttpStatusCode")
                    .value(200)
            );
    }

}
