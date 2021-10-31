package org.programmers.interparkyu.roundSeat;

import java.time.LocalDate;
import java.util.List;
import org.programmers.interparkyu.RoundSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoundSeatRepository extends JpaRepository<RoundSeat, Long> {
    @Query("select rs from RoundSeat as rs join fetch rs.seat where rs.round.performance.id = :performanceId and rs.round.date = :date order by rs.round.id, rs.id")
    List<RoundSeat> findAllRoundByPerformanceIdAndDate(Long performanceId, LocalDate date);
}
