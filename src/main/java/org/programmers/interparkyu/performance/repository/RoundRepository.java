package org.programmers.interparkyu.performance.repository;

import java.time.LocalDate;
import java.util.List;
import org.programmers.interparkyu.performance.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findAllByPerformanceId(Long performanceId);

    List<Round> findAllByPerformanceIdAndDateOrderByRoundAsc(Long performanceId, LocalDate date);

    List<Round> findAllByPerformanceIdAndDateAndRoundOrderById(Long performanceId, LocalDate date, Integer round);
}
