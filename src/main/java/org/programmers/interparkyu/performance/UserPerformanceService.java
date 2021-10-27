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

    private final PerformanceConverter converter;

    public UserPerformanceService(
        UserPerformanceRepository repository, PerformanceConverter converter
    ) {
        this.repository = repository;
        this.converter = converter;
    }

    @Transactional(readOnly = true)
    public List<BriefPerformanceInfo> getAllPerformance() {
        List<Performance> performances = repository.findAll();
        return performances.stream()
            .filter(performance ->
                LocalDate.now().isBefore(performance.getEndDate())
            )
            .map(converter::convertToBriefPerformanceInfo).toList();
    }
}
