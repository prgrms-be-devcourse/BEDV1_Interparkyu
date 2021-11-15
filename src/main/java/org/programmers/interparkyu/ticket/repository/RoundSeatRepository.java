package org.programmers.interparkyu.ticket.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.programmers.interparkyu.hall.domain.Seat;
import org.programmers.interparkyu.performance.domain.Round;
import org.programmers.interparkyu.ticket.domain.RoundSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoundSeatRepository extends JpaRepository<RoundSeat, Long> {

    @Query("select rs from RoundSeat as rs join fetch rs.seat where rs.round.id = :roundId")
    List<RoundSeat> findAllByRoundId(Long roundId);

    Optional<RoundSeat> findByRoundAndSeat(Round round, Seat seat);

//    @Lock(LockModeType.PESSIMISTIC_READ)
    RoundSeat readById(Long roundId);

}
