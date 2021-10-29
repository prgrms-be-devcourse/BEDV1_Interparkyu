package org.programmers.interparkyu.hall;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<Hall, Long> {

  Optional<Hall> findByName(String name);

}
