package org.programmers.interparkyu.performance.repository;

import org.programmers.interparkyu.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPerformanceRepository extends JpaRepository<Performance, Long> {

}
