package org.programmers.interparkyu.performance.service;

import static org.programmers.interparkyu.common.utils.TimeUtil.toLocalDate;

import java.text.MessageFormat;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.common.error.exception.NotFoundException;
import org.programmers.interparkyu.hall.service.HallService;
import org.programmers.interparkyu.performance.domain.Performance;
import org.programmers.interparkyu.performance.domain.PerformanceCategory;
import org.programmers.interparkyu.performance.dto.request.PerformanceCreateRequest;
import org.programmers.interparkyu.performance.dto.request.PerformanceModifyRequest;
import org.programmers.interparkyu.performance.dto.response.PerformanceCreateResponse;
import org.programmers.interparkyu.performance.dto.response.PerformanceModifyResponse;
import org.programmers.interparkyu.performance.repository.PerformanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class AdminPerformanceService {

    private final PerformanceRepository performanceRepository;

    private final HallService hallService;

    @Transactional
    public PerformanceCreateResponse createPerformance(
        PerformanceCreateRequest performanceCreateRequest) {
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
    public PerformanceModifyResponse modifyPerformance(final Long id,
        PerformanceModifyRequest performanceModifyRequest) {
        Performance performance = performanceRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                MessageFormat.format("Cannot find the performance. (ID : {0})", id)));
        performance.changeMetaData(
            performanceModifyRequest.title(),
            Integer.parseInt(performanceModifyRequest.runtime()),
            PerformanceCategory.of(performanceModifyRequest.category()),
            hallService.findIdByName(performanceModifyRequest.hallName())
        );

        performance.changeDate(
            toLocalDate(performanceModifyRequest.startDate()),
            toLocalDate(performanceModifyRequest.startDate())
        );

        return PerformanceModifyResponse.from(performance.getId());
    }

    @Transactional
    public void deletePerformance(final Long id) {
        performanceRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Performance findPerformance(final Long id) {
        return performanceRepository.findById(id).orElseThrow(() -> new NotFoundException(
            MessageFormat.format("Cannot find the performance. (ID : {0})", id)));
    }

}
