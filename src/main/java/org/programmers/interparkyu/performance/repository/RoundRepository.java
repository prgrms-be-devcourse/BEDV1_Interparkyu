package org.programmers.interparkyu.performance.repository;

import java.time.LocalDate;
import java.util.List;
import org.programmers.interparkyu.performance.domain.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoundRepository extends JpaRepository<Round, Long> {

    List<Round> findAllByPerformanceId(Long performanceId);

    List<Round> findAllByPerformanceIdAndDateOrderByRoundAsc(Long performanceId, LocalDate date);

    List<Round> findAllByPerformanceIdAndDateAndRoundOrderById(Long performanceId, LocalDate date, Integer round);

    @Query(value = "select remaining_seats from rounds where id=:id", nativeQuery = true)
    int findRemainingSeat(@Param("id") Long roundId);
}
