package org.programmers.interparkyu.performance;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.programmers.interparkyu.performance.dto.BriefPerformanceInfo;
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
}
