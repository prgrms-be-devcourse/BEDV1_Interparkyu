package org.programmers.interparkyu.performance.service;

import static org.programmers.interparkyu.utils.TimeUtil.toLocalDate;

import java.text.MessageFormat;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.error.exception.NotFoundException;
import org.programmers.interparkyu.hall.HallService;
import org.programmers.interparkyu.performance.Performance;
import org.programmers.interparkyu.performance.PerformanceCategory;
import org.programmers.interparkyu.performance.dto.PerformanceModifyRequest;
import org.programmers.interparkyu.performance.dto.PerformanceModifyResponse;
import org.programmers.interparkyu.performance.repository.PerformanceRepository;
import org.programmers.interparkyu.performance.dto.PerformanceCreateRequest;
import org.programmers.interparkyu.performance.dto.PerformanceCreateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
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

  @Transactional
  public PerformanceModifyResponse modifyPerformance(final Long id, PerformanceModifyRequest performanceModifyRequest){
    Performance performance = performanceRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 공연을 찾을 수 없습니다."));
    performance.changeMetaData(
        performanceModifyRequest.title(),
        Integer.parseInt(performanceModifyRequest.runtime()),
        PerformanceCategory.of(performanceModifyRequest.category()),
        hallService.findIdByName(performanceModifyRequest.hallName()));

    performance.changeDate(
        toLocalDate(performanceModifyRequest.startDate()),
        toLocalDate(performanceModifyRequest.startDate())
    );

    return PerformanceModifyResponse.from(performance.getId());
  }

  @Transactional
  public void deletePerformance(final Long id){
    performanceRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public Performance findPerformanceById(final Long id){
    return performanceRepository.findById(id).orElseThrow(() -> new NotFoundException(
        MessageFormat.format("id : {0}, 해당 공연을 찾을 수 없습니다.", id)));
  }

}
