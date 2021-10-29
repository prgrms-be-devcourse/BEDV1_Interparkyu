package org.programmers.interparkyu.performance.service;

import static org.programmers.interparkyu.utils.TimeUtil.toLocalDate;

import lombok.AllArgsConstructor;
import org.programmers.interparkyu.hall.HallService;
import org.programmers.interparkyu.performance.Performance;
import org.programmers.interparkyu.performance.PerformanceCategory;
import org.programmers.interparkyu.performance.repository.PerformanceRepository;
import org.programmers.interparkyu.performance.dto.PerformanceCreateRequest;
import org.programmers.interparkyu.performance.dto.PerformanceCreateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional
@Service
public class AdminPerformanceService {

  private final PerformanceRepository performanceRepository;

  private final HallService hallService;

  @Transactional
  public PerformanceCreateResponse createPerformance(PerformanceCreateRequest performanceCreateRequest){
    Performance performance = Performance.builder()
        .title(performanceCreateRequest.title())
        .startDate(toLocalDate(performanceCreateRequest.startDate()))
        .endDate(toLocalDate(performanceCreateRequest.endDate()))
        .runtime(Integer.parseInt(performanceCreateRequest.runtime()))
        .category(PerformanceCategory.of(performanceCreateRequest.category()))
        .hall(hallService.findIdByName(performanceCreateRequest.hallName()))
        .build();

    return PerformanceCreateResponse.from(performanceRepository.save(performance).getId());
  }

}
