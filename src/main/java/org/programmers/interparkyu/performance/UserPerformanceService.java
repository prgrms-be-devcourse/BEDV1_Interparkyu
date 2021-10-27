package org.programmers.interparkyu.performance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.programmers.interparkyu.performance.dto.BriefPerformanceInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class UserPerformanceService {

    private final UserPerformanceRepository repository;

    public UserPerformanceService(UserPerformanceRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<BriefPerformanceInfo> getAllPerformance() {
        List<Performance> performances = repository.findAll();
        return performances.stream()
            .filter(performance ->
                LocalDate.now().isBefore(performance.getEndDate())
            )
            .map(performance -> BriefPerformanceInfo.from(
                performance.getTitle(),
                performance.getCategory(),
                performance.getHall().getName()
            )).toList();
    }
}
