package org.programmers.interparkyu.hall.repository;

import org.programmers.interparkyu.hall.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
