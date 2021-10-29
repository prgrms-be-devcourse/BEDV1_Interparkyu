package org.programmers.interparkyu.performance.service;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.error.exception.NotFoundException;
import org.programmers.interparkyu.performance.Performance;
import org.programmers.interparkyu.performance.repository.UserPerformanceRepository;
import org.programmers.interparkyu.performance.dto.BriefPerformanceInfo;
import org.programmers.interparkyu.performance.dto.PerformanceSummary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserPerformanceService {

    private final UserPerformanceRepository repository;

    @Transactional(readOnly = true)
    public List<BriefPerformanceInfo> getAllOnStagePerformanceList() {
        List<Performance> performances = repository.findAll();
        return performances.stream()
            .filter(performance ->
                LocalDate.now().isBefore(performance.getEndDate())
            )
            .map(BriefPerformanceInfo::from).toList();
    }

    @Transactional(readOnly = true)
    public PerformanceSummary getPerformanceById(Long performanceId) {
        Performance performance = repository.findById(performanceId)
            .orElseThrow(() -> new NotFoundException("No such performance exist"));
        return PerformanceSummary.from(performance);
    }
}
