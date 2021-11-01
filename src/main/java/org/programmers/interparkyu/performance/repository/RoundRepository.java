package org.programmers.interparkyu.performance.repository;

import java.time.LocalDate;
import java.util.List;
import org.programmers.interparkyu.performance.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findAllByPerformanceId(Long performanceId);

    @Query("select r from Round as r where r.performance.id = :performanceId and r.date = :date order by r.round")
    List<Round> findAllByPerformanceIdAndDate(Long performanceId, LocalDate date);
}
