package org.programmers.interparkyu.performance.service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.common.error.exception.NotFoundException;
import org.programmers.interparkyu.performance.domain.Performance;
import org.programmers.interparkyu.performance.dto.response.BriefPerformanceResponse;
import org.programmers.interparkyu.performance.dto.response.PerformanceSummaryResponse;
import org.programmers.interparkyu.performance.repository.PerformanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserPerformanceService {

    private final PerformanceRepository repository;

    @Transactional(readOnly = true)
    public List<BriefPerformanceResponse> getAllOnStagePerformanceList(String requestBaseUrl) {
        List<Performance> performances = repository.findAll();
        return performances.stream()
            .filter(performance ->
                LocalDate.now().isBefore(performance.getEndDate())
            )
            .map(performance -> BriefPerformanceResponse.from(performance, requestBaseUrl))
            .toList();
    }

    @Transactional(readOnly = true)
    public PerformanceSummaryResponse getPerformanceSummary(final Long performanceId) {
        Performance performance = repository.findById(performanceId)
            .orElseThrow(() ->
                new NotFoundException(
                    MessageFormat.format(
                        "No such performance exist. (Given ID: {0})", performanceId)));
        return PerformanceSummaryResponse.from(performance);
    }

}
