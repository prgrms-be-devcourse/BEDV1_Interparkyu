package org.programmers.interparkyu.roundSeat;

import java.util.List;
import java.util.Optional;
import org.programmers.interparkyu.RoundSeat;
import org.programmers.interparkyu.hall.Seat;
import org.programmers.interparkyu.performance.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoundSeatRepository extends JpaRepository<RoundSeat, Long> {
    @Query("select rs from RoundSeat rs join fetch rs.seat")
    List<RoundSeat> findAllByRoundId(Long roundId);

    Optional<RoundSeat> findByRoundAndSeat(Round round, Seat seat);
}
