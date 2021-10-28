package org.programmers.interparkyu.performance.controller;

import static org.programmers.interparkyu.performance.controller.AdminPerformanceController.performanceRequestUri;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.interparkyu.hall.Hall;
import org.programmers.interparkyu.hall.HallRepository;
import org.programmers.interparkyu.performance.dto.PerformanceCreateRequest;
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
  public void save() throws Exception {
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

}