package org.programmers.interparkyu.performance.controller;

import static org.programmers.interparkyu.performance.controller.AdminPerformanceController.performanceRequestUri;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.interparkyu.hall.Hall;
import org.programmers.interparkyu.hall.HallRepository;
import org.programmers.interparkyu.performance.dto.PerformanceCreateRequest;
import org.programmers.interparkyu.performance.dto.PerformanceCreateResponse;
import org.programmers.interparkyu.performance.dto.PerformanceModifyRequest;
import org.programmers.interparkyu.performance.repository.PerformanceRepository;
import org.programmers.interparkyu.performance.service.AdminPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
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

  private Hall hall;

  @BeforeEach
  void setUp(){
    hall = Hall.builder()
        .name("올림픽홀")
        .seatCount(300)
        .build();

    hallRepository.save(hall);
  }

  @Test
  @DisplayName("공연 정보를 등록할 수 있다.")
  public void savePerformanceTest() throws Exception {
    // Given
    PerformanceCreateRequest request = new PerformanceCreateRequest(
        "방탄소년단", "20301010", "20301110", "180", "CONCERT", "올림픽홀");

    // When Then
    mockMvc
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
        .andExpect(
            MockMvcResultMatchers
                .jsonPath("$.data[0].id")
                .value(1L)
        );
  }

  @Test
  @DisplayName("공연 정보를 수정할 수 있다.")
  public void modifyPerformanceTest() throws Exception {
    // Given
    hall = Hall.builder()
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

    PerformanceCreateResponse createResponse = adminPerformanceService.createPerformance(createRequest);

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
                .jsonPath("$.data[0].id")
                .value(2L)
        );
  }

}