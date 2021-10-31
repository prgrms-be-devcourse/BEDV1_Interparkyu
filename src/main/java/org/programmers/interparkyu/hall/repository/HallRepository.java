package org.programmers.interparkyu.hall.repository;

import java.util.Optional;
import org.programmers.interparkyu.hall.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<Hall, Long> {

  Optional<Hall> findByName(String name);

}
