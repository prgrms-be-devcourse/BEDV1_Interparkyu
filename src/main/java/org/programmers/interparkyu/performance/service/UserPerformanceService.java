package org.programmers.interparkyu.performance.service;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.common.error.exception.NotFoundException;
import org.programmers.interparkyu.performance.domain.Performance;
import org.programmers.interparkyu.performance.repository.PerformanceRepository;
import org.programmers.interparkyu.performance.dto.BriefPerformanceInfo;
import org.programmers.interparkyu.performance.dto.PerformanceSummary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserPerformanceService {

    private final PerformanceRepository repository;

    @Transactional(readOnly = true)
    public List<BriefPerformanceInfo> getAllOnStagePerformanceList(String requestBaseUrl) {
        List<Performance> performances = repository.findAll();
        return performances.stream()
            .filter(performance ->
                LocalDate.now().isBefore(performance.getEndDate())
            )
            .map(performance -> BriefPerformanceInfo.from(performance, requestBaseUrl)).toList();
    }

    @Transactional(readOnly = true)
    public PerformanceSummary getPerformanceById(Long performanceId) {
        Performance performance = repository.findById(performanceId)
            .orElseThrow(() -> new NotFoundException("No such performance exist"));
        return PerformanceSummary.from(performance);
    }
}
