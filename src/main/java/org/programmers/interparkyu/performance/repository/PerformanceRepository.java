package org.programmers.interparkyu.performance.repository;

import org.programmers.interparkyu.performance.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {

}
