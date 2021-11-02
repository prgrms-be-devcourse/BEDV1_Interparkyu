package org.programmers.interparkyu.ticket.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.programmers.interparkyu.ticket.domain.RoundSeat;
import org.programmers.interparkyu.hall.domain.Seat;
import org.programmers.interparkyu.performance.domain.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoundSeatRepository extends JpaRepository<RoundSeat, Long> {
    @Query("select rs from RoundSeat as rs join fetch rs.seat where rs.round.performance.id = :performanceId and rs.round.date = :date order by rs.round.id, rs.id")
    List<RoundSeat> findAllRoundByPerformanceIdAndDate(Long performanceId, LocalDate date);

    @Query("select rs from RoundSeat as rs join fetch rs.seat where rs.round.id = :roundId")
    List<RoundSeat> findAllByRoundId(Long roundId);

    Optional<RoundSeat> findByRoundAndSeat(Round round, Seat seat);
}
