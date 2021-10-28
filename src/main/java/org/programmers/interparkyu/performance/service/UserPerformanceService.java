package org.programmers.interparkyu.performance.service;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
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
        // TODO 2020.10.28 TI-25 : TI-72번(Global Exception 처리) 완료되면 잘못된 공연 요청에 대한 예외를 던지도록 수정
        Performance performance = repository.findById(performanceId)
            .orElseThrow(() -> new RuntimeException("No such performance exist"));
        return PerformanceSummary.from(performance);
    }
}
